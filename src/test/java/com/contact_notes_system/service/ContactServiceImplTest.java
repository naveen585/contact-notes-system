package com.contact_notes_system.service;

import com.contact_notes_system.dto.ContactDTO;
import com.contact_notes_system.entity.Contact;
import com.contact_notes_system.entity.UserInfo;
import com.contact_notes_system.repository.ContactRepository;
import com.contact_notes_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserRepository userRepository;

    private UserInfo user;
    private Contact contact;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserInfo();
        user.setId(1L);
        user.setUsername("linquser");
        user.setIsDeleted("N");

        contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("Naveen");
        contact.setIsDeleted("N");
        contact.setUser(user);

        contactDTO = new ContactDTO();
        contactDTO.setFirstName("Joe");
        contactDTO.setLastName("Doe");
        contactDTO.setEmail("joe@example.com");
        contactDTO.setPhone(1234567890);
    }

    @Test
    void testGetContactNotFound() {
        when(userRepository.findByUsername("linquser")).thenReturn(Optional.of(user));
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> contactService.getContactById(1L, "linquser"));
    }

    @Test
    void testGetUserContactInvalid() {
        UserInfo anotherUser = new UserInfo();
        anotherUser.setId(2L);
        contact.setUser(anotherUser);

        when(userRepository.findByUsername("linquser")).thenReturn(Optional.of(user));
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        assertThrows(EntityNotFoundException.class, () -> {
            contactService.getUserContact(1L, "linquser");
        });
    }

    @Test
    void testGetUserContactAccessingDeletedContact() {
        contact.setIsDeleted("Y");
        when(userRepository.findByUsername("linquser")).thenReturn(Optional.of(user));
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        assertThrows(EntityNotFoundException.class, () -> {
            contactService.getUserContact(1L, "linquser");
        });
    }

    @Test
    void testGetUserContactNotFound() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            contactService.getUserContact(1L, "linquser");
        });
    }
}
