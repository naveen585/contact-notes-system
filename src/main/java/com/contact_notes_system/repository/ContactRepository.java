package com.contact_notes_system.repository;

import com.contact_notes_system.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Useful for accessing the contact data from the table, and we can avoid the sql queries by using JPA.
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUserId(Long userId);
}
