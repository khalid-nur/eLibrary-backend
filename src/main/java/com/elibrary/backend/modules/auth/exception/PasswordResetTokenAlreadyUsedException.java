package com.elibrary.backend.modules.auth.exception;

/**
 * Exception thrown when a password reset token has already been used
 */
public class PasswordResetTokenAlreadyUsedException extends RuntimeException {
    public PasswordResetTokenAlreadyUsedException(String message) {
        super(message);
    }
}
