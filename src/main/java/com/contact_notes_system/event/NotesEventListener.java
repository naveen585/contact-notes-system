package com.contact_notes_system.event;

import com.contact_notes_system.entity.Notes;
import com.contact_notes_system.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

//This is useful for analytics, tracking, or downstream processing.
//This will also log the events when a notes are created.
@Component
@RequiredArgsConstructor
public class NotesEventListener {

    private final NotesRepository notesRepository;

    @EventListener
    public void handleNoteCreatedEvent(NotesCreationEvent event) {
        Long noteId = event.getNoteId();

        Optional<Notes> optionalNote = notesRepository.findById(noteId);

        if (optionalNote.isPresent()) {
            Notes note = optionalNote.get();
            System.out.println("Received note creation event for ID: " + noteId);
            System.out.println("Running analytics on note: " + note.getBody());
        } else {
            System.out.println("Note not found for ID: " + noteId);
        }
    }
}

