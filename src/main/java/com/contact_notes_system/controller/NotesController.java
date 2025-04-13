package com.contact_notes_system.controller;

import com.contact_notes_system.dto.NotesDTO;
import com.contact_notes_system.service.NotesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NotesController {

    private final NotesServiceImpl notesServiceImpl;

    //User can create particular note in particular contact.
    @PostMapping("/")
    public ResponseEntity<NotesDTO> createNote(@RequestBody NotesDTO notesDTO,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User is creating notes for contact." +notesDTO.getContactId());
        return new ResponseEntity<>(notesServiceImpl
                .createNote(notesDTO, userDetails.getUsername()), HttpStatus.CREATED);
    }

    //Using this api user can access all the notes of that particular contact
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<NotesDTO>> getAllNotes(@PathVariable Long contactId,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User accessing notes of contact : "+contactId);
        return new ResponseEntity<>(notesServiceImpl
                .getNotesForContact(contactId, userDetails.getUsername()), HttpStatus.OK);
    }

    //using this api user can update particular note of a particular contact
    @PutMapping("/{noteId}")
    public ResponseEntity<NotesDTO> updateNote(@PathVariable Long noteId,
                                              @RequestBody NotesDTO notesDTO,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User updating the note :"+noteId +" of contact: "+ notesDTO.getContactId());
        return new ResponseEntity<>(notesServiceImpl
                .updateNote(noteId, notesDTO, userDetails.getUsername()), HttpStatus.ACCEPTED);
    }

    //using this api user can delete particular note
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        log.info("User deleting note :"+noteId);
        notesServiceImpl.deleteNote(noteId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}