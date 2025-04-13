package com.contact_notes_system.util;

import com.contact_notes_system.dto.ContactDTO;
import com.contact_notes_system.dto.NotesDTO;
import com.contact_notes_system.entity.Contact;
import com.contact_notes_system.entity.Notes;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CommonUtils {

    //This is common utility method for converting contact DTO to entity
    public static Contact convertContactDTOToEntity(ContactDTO contactDTO, String username){
        Contact contact =  new Contact();
        log.info("Converting the contact DTO to entity for :"+ contactDTO.getId());
        if(contactDTO.getFirstName() != null){
            contact.setFirstName(contactDTO.getFirstName());
        }
        if(contactDTO.getLastName() != null){
            contact.setLastName(contactDTO.getLastName());
        }
        if(contactDTO.getEmail() != null){
            contact.setEmail(contactDTO.getEmail());
        }
        if(contactDTO.getPhone() != 0){
            contact.setPhone(contactDTO.getPhone());
        }
        contact.setCreatedBy(username);
        log.info("contact entity is created.");
        return contact;
    }

    public static ContactDTO convertContactEntityToDTO(Contact contact) {
        List<NotesDTO> notesDTOs = new ArrayList<>();
        log.info("Converting the contact entity to DTO for :"+ contact.getId());
        if(contact.getNotes() != null) {
            notesDTOs = contact.getNotes().stream()
                    .filter(note -> note.getIsDeleted().equals("N"))
                    .map(CommonUtils::convertNotesEntityToDTO)
                    .toList();
        }
        return ContactDTO.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .userInfo(contact.getUser())
                .notesList(notesDTOs)
                .build();
    }

    public static NotesDTO convertNotesEntityToDTO(Notes notes){
        log.info("Converting the notes entity to dto for :"+ notes.getId());
        return NotesDTO.builder()
                .id(notes.getId())
                .body(notes.getBody())
                .contactId(notes.getContact().getId())
                .build();
    }


}
