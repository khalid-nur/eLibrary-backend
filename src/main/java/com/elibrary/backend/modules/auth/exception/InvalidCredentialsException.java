package com.elibrary.backend.modules.auth.exception;

/**
 * Exception thrown when invalid credentials are provided during authentication
 */
public class InvalidCredentialsException extends RuntimeException{
    /**
     * Constructor for InvalidCredentialsException
     *
     * @param message the error message describing the exception
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
