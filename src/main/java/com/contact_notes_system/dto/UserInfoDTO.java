package com.contact_notes_system.dto;

import lombok.Builder;
import lombok.Data;

// This DTO is used for transferring the data for apis which will be more secure
// and won't need to access the userInfo object and expose it.
@Data
@Builder
public class UserInfoDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
}
