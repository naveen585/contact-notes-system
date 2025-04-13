package com.contact_notes_system.service;

import com.contact_notes_system.dto.NotesDTO;
import com.contact_notes_system.entity.Contact;
import com.contact_notes_system.entity.Notes;
import com.contact_notes_system.event.NotesCreationEvent;
import com.contact_notes_system.repository.NotesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NotesServiceImplTest {

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private ContactServiceImpl contactServiceImpl;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private NotesServiceImpl notesService;

    private Contact contact;
    private Notes note;

    @BeforeEach
    void setup() {
        contact = new Contact();
        contact.setId(1L);
        contact.setIsDeleted("N");

        note = Notes.builder().id(1L).body("Testing notes.").contact(contact).build();
        note.setCreatedBy("linquser");
    }

    @Test
    void testCreateNote() {
        NotesDTO dto = new NotesDTO();
        dto.setContactId(1L);
        dto.setBody("This is for creating note");
        when(contactServiceImpl.getUserContact(1L, "linquser")).thenReturn(contact);
        when(notesRepository.save(any(Notes.class))).thenReturn(note);
        NotesDTO result = notesService.createNote(dto, "linquser");
        assertEquals("Testing notes.", result.getBody());
        Mockito.verify(notesRepository, times(2)).save(any(Notes.class));
        Mockito.verify(eventPublisher, times(1)).publishEvent(any(NotesCreationEvent.class));
    }

    @Test
    void testGetNotesForContactNotFound() {
        when(contactServiceImpl.getUserContact(1L, "notlinquser"))
                .thenThrow(new EntityNotFoundException("Contact not found"));
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            notesService.getNotesForContact(1L, "notlinquser");
        });
        assertEquals("Contact not found", thrown.getMessage());
    }

    @Test
    void testGetUserNotesAccessingAnotherContactNotes() {
        note.setIsDeleted("N");
        note.setContact(contact);
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));
        when(contactServiceImpl.getUserInfo("unauthorizedUser")).thenThrow(
                new EntityNotFoundException("User not found"));
        assertThrows(NullPointerException.class, () -> {
            notesService.updateNote(1L, new NotesDTO(), "unauthorizedUser");
        });
    }
}

