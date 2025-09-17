package com.elibrary.backend.modules.checkout.exception;

/**
 * Exception thrown when attempting to return a book that has already been returned
 */
public class BookAlreadyReturnedException extends RuntimeException {
    public BookAlreadyReturnedException(String message) {
        super(message);
    }
}
