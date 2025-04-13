package com.contact_notes_system.service;

import com.contact_notes_system.dto.NotesDTO;

import java.util.List;

public interface NotesService {
    NotesDTO createNote(NotesDTO notesDTO, String username);
    List<NotesDTO> getNotesForContact(Long contactId, String username);
    NotesDTO updateNote(Long noteId, NotesDTO notesDTO, String username);
    void deleteNote(Long noteId, String username);
}
