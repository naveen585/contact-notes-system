package com.contact_notes_system.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginRequestTest {

    @Test
    public void testLoginRequestTestValidFields(){
        LoginRequest loginRequest = new LoginRequest("linqUser","linq");
        assertEquals(loginRequest.getUsername(),"linqUser");
        assertEquals(loginRequest.getPassword(),"linq");
    }

    @Test
    public void testLoginRequestTestInvalidFields(){
        LoginRequest loginRequest = new LoginRequest("linqUser","linq");
        assertNotEquals(loginRequest.getUsername(),"notLinqUser");
        assertNotEquals(loginRequest.getPassword(),"wrongPassword");
    }
}
