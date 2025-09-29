package com.elibrary.backend.modules.emailNotification.service;

/**
 * Service interface for sending emails related to user actions
 */
public interface EmailService {

    /**
     * Sends a password reset email to the given user
     *
     * @param userName the name of the user to include in the email
     * @param to the user's email address
     * @param token the password reset token to include in the email
     */
    void sendPasswordResetEmail(String userName, String to, String token);
}
