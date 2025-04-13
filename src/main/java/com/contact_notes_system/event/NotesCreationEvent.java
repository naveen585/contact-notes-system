package com.contact_notes_system.event;

//This is an entity for creating the notes creation event.
public class NotesCreationEvent {
    private final Long noteId;

    public NotesCreationEvent(Long noteId) {
        this.noteId = noteId;
    }

    public Long getNoteId() {
        return noteId;
    }
}

