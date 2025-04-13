package com.contact_notes_system.dto;

import com.contact_notes_system.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// This DTO is used for transferring the data for apis which will be more secure
// and won't need to access the contact object and expose it.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private List<NotesDTO> notesList;

    private UserInfo userInfo;
}
