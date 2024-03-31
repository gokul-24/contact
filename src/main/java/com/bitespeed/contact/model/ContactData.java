package com.bitespeed.contact.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ContactData {
    @Id
    private int primaryContactId;
    private List<String> emails = new ArrayList<>();
    private List<String> phoneNumbers= new ArrayList<>();
    private List<Integer> secondaryContactIds= new ArrayList<>();

    public void setEmails(String email){
        if(email!=null && !this.emails.contains(email))
        {
            this.emails.add(email);
        }
    }
    public void setPhoneNumbers(String num)
    {
        if(num!=null && !this.phoneNumbers.contains(num))
        {
            this.phoneNumbers.add(num);
        }
    }
    public void setSecondaryContactIds(int id){
        if(!this.secondaryContactIds.contains(id))
        {
            this.secondaryContactIds.add(id);
        }
    }
}