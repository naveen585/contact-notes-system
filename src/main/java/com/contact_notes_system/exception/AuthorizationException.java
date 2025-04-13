package com.contact_notes_system.exception;

//This is a custom exception useful for when user faced authorization issues.
public class AuthorizationException extends Exception{
    public AuthorizationException(String message){
        super(message);
    }
}
