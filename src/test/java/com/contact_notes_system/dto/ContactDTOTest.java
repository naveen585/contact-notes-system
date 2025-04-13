package com.contact_notes_system.dto;

import com.contact_notes_system.entity.UserInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDTOTest {

    @Test
    public void testContactDTOValidFieldsWithConstructor() {
        ContactDTO contactDTO = new ContactDTO(1L, "John", "Doe", "john.doe@example.com", 1234567890, new ArrayList<>(), new UserInfo());
        assertEquals(1L, contactDTO.getId());
        assertEquals(contactDTO.getFirstName(), "John");
        assertEquals(contactDTO.getLastName(), "Doe");
        assertEquals(contactDTO.getEmail(), "john.doe@example.com");
        assertEquals(contactDTO.getPhone(), 1234567890);
        assertEquals(0, contactDTO.getNotesList().size());
        assertNotEquals(new UserInfo(), contactDTO.getUserInfo());
    }

    @Test
    public void testContactDTOValidFieldsWithBuilder() {
        ContactDTO contactDTO = ContactDTO.builder()
                .id(2L)
                .firstName("Naveen")
                .lastName("Kumar")
                .email("samplemail@gmail.com")
                .phone(1234567891)
                .notesList(new ArrayList<>())
                .userInfo(new UserInfo())
                .build();
        assertEquals(2L, contactDTO.getId());
        assertEquals(contactDTO.getFirstName(), "Naveen");
        assertEquals(contactDTO.getLastName(), "Kumar");
        assertEquals(contactDTO.getEmail(), "samplemail@gmail.com");
        assertEquals(contactDTO.getPhone(), 1234567891);
    }

    @Test
    void testContactDTOInvalidFields() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(1L);
        contactDTO.setFirstName("J");
        contactDTO.setLastName("Doe");
        contactDTO.setEmail("john.doe@example.com");
        contactDTO.setPhone(1234567890);
        assertNotEquals("John", contactDTO.getFirstName());
        assertNotEquals("Do", contactDTO.getLastName());
    }

}

