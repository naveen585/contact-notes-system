package com.contact_notes_system.service;

import com.contact_notes_system.dto.ContactDTO;
import com.contact_notes_system.entity.Contact;
import com.contact_notes_system.entity.UserInfo;
import com.contact_notes_system.repository.ContactRepository;
import com.contact_notes_system.repository.UserRepository;
import com.contact_notes_system.util.CommonUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    @Autowired
    private final ContactRepository contactRepository;

    @Autowired
    private final UserRepository userRepository;

    //useful for creating the contact
    @Override
    @Transactional
    public ContactDTO createContact(ContactDTO contactDTO, String username) {
        Contact contact = CommonUtils.convertContactDTOToEntity(contactDTO, username);
        contact.setUser(getUserInfo(username));
        log.info("Creating the contact for user : "+ username);
        return CommonUtils
                .convertContactEntityToDTO(contactRepository.save(contact));
    }

    @Override
    public List<ContactDTO> getAllContacts(String username) {
        log.info("Accessing all the contacts by user : "+username);
        return contactRepository.findByUserId(getUserInfo(username).getId())
                .stream()
                .filter(contact -> contact.getIsDeleted().equals("N"))
                .map(CommonUtils::convertContactEntityToDTO)
                .toList();
    }

    @Override
    @Transactional
    public ContactDTO getContactById(Long id, String username) {
        log.info("Accessing a particular contact with id "+ id+ "by user :"+username);
        return CommonUtils
                .convertContactEntityToDTO(getUserContact(id, username));
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO, String username) {
        log.info("updating a particular contact with id "+ id+ "by user :"+username);
        Contact contact = getUserContact(id, username);
        if (contactDTO.getFirstName() != null) contact.setFirstName(contactDTO.getFirstName());
        if (contactDTO.getLastName() != null) contact.setLastName(contactDTO.getLastName());
        if (contactDTO.getEmail() != null) contact.setEmail(contactDTO.getEmail());
        if (contactDTO.getPhone() != 0) contact.setPhone(contactDTO.getPhone());
        log.info("contact has been updated for id: "+id);
        return CommonUtils.convertContactEntityToDTO(contactRepository.save(contact));
    }

    @Override
    public void deleteContact(Long id, String username) {
        log.info("Safe deleting the contact :"+id+" by user: "+username);
        Contact contact = getUserContact(id, username);
        contact.setIsDeleted("Y");
        contact.setDeletedBy(username);
        contactRepository.save(contact);
    }

    //Common method for fetching the contact details with id.
    public Contact getUserContact(Long id, String username) {
        log.info("fetching the contact :"+id+" by user: "+username);
        return contactRepository.findById(id)
                .filter(c -> c.getUser().getId().equals(getUserInfo(username).getId()) && c.getIsDeleted().equals("N"))
                .orElseThrow(() -> new EntityNotFoundException("Contact not found or not yours"));
    }

    //fetching the user details by username.
    public UserInfo getUserInfo(String username) {
        log.info("fetching the user: "+username);
        return userRepository.findByUsername(username)
                .filter(userInfo -> userInfo.getIsDeleted().equals("N"))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
