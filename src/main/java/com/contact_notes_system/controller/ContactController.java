package com.contact_notes_system.controller;

import com.contact_notes_system.dto.ContactDTO;
import com.contact_notes_system.service.ContactServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {

    private final ContactServiceImpl contactServiceImpl;

    //User can create new contact using contact api.
    @PostMapping("/")
    public ResponseEntity<ContactDTO> createContact(
            @RequestBody ContactDTO contactDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("New contact is being created.");
        return new ResponseEntity<>(contactServiceImpl
                .createContact(contactDTO, userDetails.getUsername()), HttpStatus.CREATED);
    }

    //User can access all the contacts which are created by user itself.
    @GetMapping("/")
    public ResponseEntity<List<ContactDTO>> getAllContacts(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User is accessing the contacts created by the user.");
        return new ResponseEntity<>(contactServiceImpl
                .getAllContacts(userDetails.getUsername()), HttpStatus.OK);
    }

    //By using this api user can access and view particular contact details using id.

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User accessing the contact : " + id);
        return new ResponseEntity<>(contactServiceImpl
                .getContactById(id, userDetails.getUsername()), HttpStatus.OK);
    }

    //User can update the required details for a particular contact.
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(
            @PathVariable Long id,
            @RequestBody ContactDTO contactDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User updating the details of a particular contact: "+id);
        return new ResponseEntity<>(contactServiceImpl
                .updateContact(id, contactDTO, userDetails.getUsername()), HttpStatus.ACCEPTED);
    }

    //User can delete the contacts created by user itself.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User is deleting the contact: "+id);
        contactServiceImpl.deleteContact(id, userDetails.getUsername());
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.FOUND);
    }
}
