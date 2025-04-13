package com.contact_notes_system.service;

import com.contact_notes_system.dto.ContactDTO;

import java.util.List;

//By having the interface we cana ble to implement the data abstraction
// we can hide the implementation and provide the functionality for the user
public interface ContactService {
    ContactDTO createContact(ContactDTO contactDTO, String username);
    List<ContactDTO> getAllContacts(String username);
    ContactDTO getContactById(Long id, String username);
    ContactDTO updateContact(Long id, ContactDTO contactDTO, String username);
    void deleteContact(Long id, String username);
}
