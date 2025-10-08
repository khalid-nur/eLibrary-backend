package com.elibrary.backend.modules.emailNotification.service;

import java.time.LocalDate;

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
    void sendPasswordResetEmail(String userName, String to, String token) ;

    /**
     * Sends a checkout confirmation email with book details
     *
     * @param userName     the name of the user receiving the email
     * @param to           the email address of the user
     * @param checkoutDate the date the book was checked out
     * @param returnDate   the expected return date for the book
     * @param bookTitle    the title of the checked out book
     * @param bookAuthor   the author of the checked out book
     * @param bookCategory the category of the checked out book
     */
    void sendCheckoutConfirmationEmail(String userName, String to, LocalDate checkoutDate, LocalDate returnDate,
                                       String bookTitle, String bookAuthor, String bookCategory);
}
