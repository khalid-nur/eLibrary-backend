package com.elibrary.backend.common.exceptions;

/**
 * Exception thrown when a requested resource cannot be found
 */
public class ResourceNotFoundExceptions extends RuntimeException{

    /**
     * Constructor for ResourceNotFoundExceptions
     *
     * @param message the error message describing the exception
     */
    public ResourceNotFoundExceptions(String message){
        super(message);
    }
}
