package com.bitespeed.contact.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.contact.model.Contact;
import com.bitespeed.contact.model.ContactData;
import com.bitespeed.contact.model.LinkPrecedence;
import com.bitespeed.contact.repository.ContactRepo;

import jakarta.transaction.Transactional;


@Service
public class ContactService {
    @Autowired
    ContactRepo contactRepo;
    

    public List<Contact> findByEmailOrPhone(String email,String phone)
    {
        return contactRepo.findByEmailOrPhone(email, phone);
    }

    public ContactData buildContactData(int primary_id) {
        ContactData customerData = new ContactData();
        List<Contact> contacts = contactRepo.findRelatedConacts(primary_id);
        for(Contact ct:contacts){
            if(ct.getId()==primary_id)
            {
                customerData.setPrimaryContactId(primary_id);
            }
            else if(ct.getLinkedId()==primary_id)
            {
                customerData.setSecondaryContactIds(ct.getId());
            }
            customerData.setPhoneNumbers(ct.getPhoneNumber());
            customerData.setEmails(ct.getEmail());
        }

        return customerData;
        
    }

    public void update(Integer primary_id,Integer secondary_id)
    {
        Optional<Contact> contact=contactRepo.findById(secondary_id);
        Contact secondary;
        if(contact.isPresent())
        {
            secondary=contact.get();
        }
        else{
            return;
        }
        contactRepo.updateLinkedId(primary_id,secondary.getId());
        secondary.setLinkPrecedence(LinkPrecedence.secondary);
        secondary.setLinkedId(primary_id);
        secondary.setUpdatedAt(new Date());
        contactRepo.save(secondary);
        

    }

    @Transactional
    public ContactData getContactDets(Contact contact){
        List<Contact> contacts;
        contacts = this.findByEmailOrPhone(contact.getEmail(),contact.getPhoneNumber());
        if(contact.getEmail()==null && contact.getPhoneNumber()==null){
            return new ContactData();
        }
        if(contacts.size()==0){ // add new row
            contact.setLinkPrecedence(LinkPrecedence.primary);
            contact.setCreatedAt(new Date());
            //save.contact
            contact=contactRepo.save(contact);
            //since single object can build here instead of making a function call
            return this.buildContactData(contact.getId());
        }
        else
        {
            String req_email,req_phone,curr_email,curr_phone;
            req_email=contact.getEmail();
            req_phone=contact.getPhoneNumber();
            Integer primary_id=null;
            Integer primary_email_id=null;
            Integer primary_phone_id=null;
            // FIND email and phone in the same row (only display)
            // email and phone in different rows - link them based on first diff
            for(Contact ct:contacts)
            {
                boolean mail_match=false,phone_match=false;
                curr_email=ct.getEmail();
                curr_phone=ct.getPhoneNumber();
                if(req_email==null){
                    mail_match=true;
                }
                else if(curr_email!=null)
                {
                    mail_match=ct.getEmail().equals(contact.getEmail());
                }
                if(req_phone==null){
                    phone_match=true;
                }
                else if(curr_phone!=null)
                {
                    phone_match=ct.getPhoneNumber().equals(contact.getPhoneNumber());
                }

                if(mail_match && phone_match)
                {
                    
                    if(ct.getLinkedId()==null) //find primary id
                    {
                        primary_id=ct.getId();
                    }
                    else{ // else if contact is secondary find linkedId
                        primary_id=ct.getLinkedId();
                    }
                    break;
                }
                else if(mail_match)
                {
                    if(ct.getLinkedId()==null)
                    {
                        primary_email_id=ct.getId();
                    }
                    else{
                        primary_email_id=ct.getLinkedId();
                    }
                }
                else if(phone_match)
                {
                    if(ct.getLinkedId()==null)
                    {
                        primary_phone_id=ct.getId();
                    }
                    else{
                        primary_phone_id=ct.getLinkedId();
                    }
                }
            }
            if(primary_id==null) // implies email and phone are matched in diff rows
            {
                // TODO :update linkedId based on first apprearing contact
                // What if phone or mobile is matched to a secondary how to proceed - link it to primary of that secondary or create new row
                if(primary_email_id!=null && primary_phone_id!=null)
                {
                    if(primary_email_id>primary_phone_id) //check first occurrance
                    {
                        //set primary_phone as the new primary
                        primary_id=primary_phone_id;
                        this.update(primary_id,primary_email_id);

                    }
                    else{
                        //set primary_email as the new primary
                        primary_id=primary_email_id; 
                        this.update(primary_id,primary_phone_id);
                    }
                    return this.buildContactData(primary_id);
                }
                else // if one of email and phone is null and other is a match in database
                    // link it to the existing contact.
                {
                    if(primary_email_id==null){
                        primary_id=primary_phone_id;
                        contact.setLinkedId(primary_phone_id);
                    }
                    else{
                        primary_id=primary_email_id;
                        contact.setLinkedId(primary_email_id);
                    }
                    contact.setLinkPrecedence(LinkPrecedence.secondary);
                    contact.setCreatedAt(new Date());
                    contactRepo.save(contact);
                    return this.buildContactData(primary_id);
                }
                
            }
            else
            {
                return this.buildContactData(primary_id);

            }
        }

    }

    public List<Contact> getAll() {
        return contactRepo.findAll();
    }
}

