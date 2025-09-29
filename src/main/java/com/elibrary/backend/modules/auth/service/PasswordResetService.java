package com.elibrary.backend.modules.auth.service;

/**
 * Service interface for password reset operations
 */
public interface PasswordResetService {

    /**
     * Generates a password reset token for the user with the given email and sends the token to their email address
     *
     * @param userEmail the email of the user requesting a password reset
     */
    void generatePasswordResetToken(String userEmail);

    /**
     * Resets the password for a user using the provided reset token
     *
     * @param token the password reset token
     * @param newPassword the new password to set for the user
     */
    void resetPassword(String token, String newPassword);


}
