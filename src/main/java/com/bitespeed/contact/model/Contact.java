package com.bitespeed.contact.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    
    private String phoneNumber;
    
    private Integer linkedId;
    
    @Enumerated(EnumType.STRING)
    private LinkPrecedence linkPrecedence;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private Date deletedAt;
}
