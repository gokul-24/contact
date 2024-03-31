package com.bitespeed.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitespeed.contact.model.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Integer>{

    @Query(value = "SELECT * FROM Contact c WHERE c.email = :email OR c.phone_number = :phone", nativeQuery = true)
    List<Contact> findByEmailOrPhone(String email,String phone);

    @Modifying
    @Query(value = "UPDATE Contact c SET c.linked_id = :id, c.updated_at = CURRENT_TIMESTAMP WHERE c.linked_id = :id2", nativeQuery = true)
    void updateLinkedId(int id, int id2);

    @Query(value = "SELECT * FROM Contact c WHERE c.id=:primary_id OR c.linked_id=:primary_id",nativeQuery = true)
    List<Contact> findRelatedConacts(int primary_id);
}
