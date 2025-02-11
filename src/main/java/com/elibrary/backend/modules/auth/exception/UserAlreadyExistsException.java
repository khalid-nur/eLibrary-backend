package com.elibrary.backend.modules.auth.exception;

/**
 * Exception thrown when a user attempts to register with an email that already exists
 */
public class UserAlreadyExistsException extends RuntimeException{

    /**
     * Constructor for UserAlreadyExistsException
     *
     * @param message the error message describing the exception
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
