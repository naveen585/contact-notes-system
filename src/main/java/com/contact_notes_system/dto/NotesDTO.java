package com.contact_notes_system.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

// This DTO is used for transferring the data for apis which will be more secure
// and won't need to access the Notes object and expose it.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesDTO {
    private Long id;
    //This annotation is used for Field normalisation user can use any of these names to provide data for notes.
    @JsonAlias({"note_text", "note_body"})
    private String body;
    private Long contactId;
}
