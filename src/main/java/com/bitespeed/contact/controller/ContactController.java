package com.bitespeed.contact.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.contact.model.Contact;
import com.bitespeed.contact.model.ContactData;
import com.bitespeed.contact.service.ContactService;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Data
class ContactResponse {
    private ContactData contact;
}
@RestController
@RequestMapping("")
public class ContactController {
    @Autowired
    ContactService contactService;
    
    @GetMapping("all")
    public List<Contact> getAllDets() {
        return contactService.getAll();
    }
    
    @PostMapping("identify")
    public ResponseEntity<?> getContactDets(@RequestBody Contact contact)
    {
        ContactData contactData=contactService.getContactDets(contact);
        ContactResponse contactResponse=new ContactResponse();
        contactResponse.setContact(contactData);
        return ResponseEntity.ok(contactResponse);
    }
    
    
}
