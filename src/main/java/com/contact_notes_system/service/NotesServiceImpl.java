package com.contact_notes_system.service;

import com.contact_notes_system.dto.NotesDTO;
import com.contact_notes_system.entity.Contact;
import com.contact_notes_system.entity.Notes;
import com.contact_notes_system.event.NotesCreationEvent;
import com.contact_notes_system.repository.NotesRepository;
import com.contact_notes_system.util.CommonUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

    @Autowired
    private final NotesRepository notesRepository;
    @Autowired
    private final ContactServiceImpl contactServiceImpl;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public NotesDTO createNote(NotesDTO notesDTO, String username) {
        Contact contact = contactServiceImpl.getUserContact(notesDTO.getContactId(), username);
        Notes note = Notes.builder()
                .body(notesDTO.getBody())
                .contact(contact)
                .build();
        note.setCreatedBy(username);
        log.info("Notes for contact : "+notesDTO.getContactId()+" for user: "+username+" is being created.");
        notesRepository.save(note);
        eventPublisher.publishEvent(new NotesCreationEvent(note.getId()));
        return CommonUtils.convertNotesEntityToDTO(notesRepository.save(note));
    }


    public List<NotesDTO> getNotesForContact(Long contactId, String username) {
        Contact contact = contactServiceImpl.getUserContact(contactId, username);
        log.info("Accessing notes for contact : "+contactId+" for user: "+username);
        return notesRepository.findByContactId(contact.getId())
                .stream()
                .filter(notes -> notes.getIsDeleted().equals("N"))
                .map(CommonUtils::convertNotesEntityToDTO)
                .toList();
    }

    public NotesDTO updateNote(Long noteId, NotesDTO notesDTO, String username) {
        Notes note = getUserNotes(noteId, username);
        if (notesDTO.getBody() != null) {
            note.setBody(notesDTO.getBody());
        }
        note.setCreatedBy(username);
        log.info("Notes for contact : "+notesDTO.getContactId()+" for user: "+username+" is being updated");
        return CommonUtils.convertNotesEntityToDTO(notesRepository.save(note));
    }

    public void deleteNote(Long noteId, String username) {
        log.info("Notes  : "+noteId+" for user: "+username+" is being safe deleted");
        Notes note = getUserNotes(noteId, username);
        note.setIsDeleted("Y");
        note.setDeletedBy(username);
        notesRepository.save(note);
    }

    private Notes getUserNotes(Long noteId, String username) {
        log.info("Notes for contact : "+noteId +" for user: "+username+" is being fetched.");
        return notesRepository.findById(noteId)
                .filter(notes -> notes.getContact().getUser().getId()
                        .equals(contactServiceImpl.getUserInfo(username).getId()) && notes.getIsDeleted().equals("N"))
                .orElseThrow(() -> new EntityNotFoundException("Notes not found or not yours"));
    }
}

