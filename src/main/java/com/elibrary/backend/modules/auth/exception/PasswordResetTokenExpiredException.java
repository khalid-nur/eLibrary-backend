package com.elibrary.backend.modules.auth.exception;

/**
 * Exception thrown when a password reset token has expired
 */
public class PasswordResetTokenExpiredException extends RuntimeException {
    public PasswordResetTokenExpiredException(String message) {
        super(message);
    }
}