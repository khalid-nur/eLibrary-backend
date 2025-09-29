package com.elibrary.backend.modules.auth.exception;

/**
 * Exception thrown when a password reset token is invalid
 */
public class InvalidPasswordResetTokenException extends RuntimeException {
    public InvalidPasswordResetTokenException(String message) {
        super(message);
    }
}
