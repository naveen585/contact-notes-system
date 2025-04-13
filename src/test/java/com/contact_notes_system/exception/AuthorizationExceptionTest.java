package com.contact_notes_system.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationExceptionTest {

    @Test
    void testAuthorizationExceptionMessage() {
        String message = "Unauthorized access";
        AuthorizationException exception = new AuthorizationException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testAuthorizationExceptionIsInstanceOfException() {
        AuthorizationException exception = new AuthorizationException("getting error");
        assertTrue(exception instanceof Exception);
    }

    @Test
    void testAuthorizationExceptionNotNull() {
        AuthorizationException exception = new AuthorizationException("Testing not null scenario.");
        assertNotNull(exception);
    }
}

