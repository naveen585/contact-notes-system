package com.contact_notes_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//This DTO is used for the login and register apis to get the user details.
@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
