package com.bitespeed.contact.model;

import java.util.Date;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;


@Data
public class Contact {
    private Long id;
    
    private String phoneNumber;
    
    private String email;
    
    private Integer linkedId;
    
    @Enumerated(EnumType.STRING)
    private LinkPrecedence linkPrecedence;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private Date deletedAt;
}
