package com.elibrary.backend.modules.emailNotification.service.Impl;

import com.elibrary.backend.modules.emailNotification.service.EmailSender;
import com.elibrary.backend.modules.emailNotification.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.Map;

/**
 * Service implementation for sending emails related to user actions
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailSender emailSender;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${frontend.url}")
    private String frontendUrl;

    private final String imageLogoPath = "/Downloads/elibrary_logo.png";


    /**
     * Sends a password reset email to the user with a reset link
     *
     * @param userName the name of the user to include in the email
     * @param to the email address of the user
     * @param token the password reset token to include in the email
     */
    public void sendPasswordResetEmail(String userName, String to, String token) {

        try {

            // Prepare subject and reset link
            String subject = "Password Reset Request";
            String resetUrl = frontendUrl + "/reset-password?token=" + token;

            // Create a new MIME email message
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // Helper for setting the email content and headers
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // Set email address and subject
            helper.setTo(to);

            // Set the email subject
            helper.setSubject(subject);

            // Prepare HTML content using Thymeleaf
            Context context = new Context();

            // Set variables for the Thymeleaf template
            context.setVariables(Map.of("userName", userName, "resetUrl", resetUrl));

            // Render the HTML content for the email using the Thymeleaf template
            String htmlContent = templateEngine.process("emailTemplates/passwordReset", context);

            // Create multipart container
            MimeMultipart mimeMultipart = new MimeMultipart("related");

            // Create a MIME body part for the HTML content
            MimeBodyPart htmlBodyPart = new MimeBodyPart();

            // Set the HTML content and content type
            htmlBodyPart.setContent(htmlContent, "text/html");

            // Add the HTML body part to the multipart container
            mimeMultipart.addBodyPart(htmlBodyPart);

            // Add embedded logo image to email
            addLogoImage(mimeMultipart, imageLogoPath);

            // Set the multipart as the email content
            mimeMessage.setContent(mimeMultipart);

            // Send the email
            emailSender.sendMail(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

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
    @Override
    public void sendCheckoutConfirmationEmail(String userName, String to, LocalDate checkoutDate, LocalDate returnDate,
                                              String bookTitle, String bookAuthor, String bookCategory) {

       try {

           String subject = "Checkout Confirmation";
           String resetUrl = frontendUrl + "/search";

           MimeMessage mimeMessage = mailSender.createMimeMessage();

           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

           helper.setTo(to);

           helper.setSubject(subject);

           Context context = new Context();

           context.setVariables(Map.of("userName", userName, "bookTitle", bookTitle, "bookAuthor", bookAuthor,"bookCategory", bookCategory,
                   "checkoutDate", checkoutDate, "returnDate", returnDate,
                   "eLibraryUrl", resetUrl));

           String htmlContent = templateEngine.process("emailTemplates/checkoutReceipt", context);

           MimeMultipart mimeMultipart = new MimeMultipart("related");

           MimeBodyPart htmlBodyPart = new MimeBodyPart();

           htmlBodyPart.setContent(htmlContent, "text/html");

           mimeMultipart.addBodyPart(htmlBodyPart);

           addLogoImage(mimeMultipart, imageLogoPath);

           mimeMessage.setContent(mimeMultipart);

           emailSender.sendMail(mimeMessage);

       } catch (Exception e) {
           throw new RuntimeException("Failed to send checkout receipt email confirmation ", e);
       }
    }

    private void addLogoImage(MimeMultipart multipart, String imagePath) throws Exception {
        // Create a MIME body part for the image
        MimeBodyPart imageBodyPart = new MimeBodyPart();

        // Load the image from the local file system
        DataSource dataSource = new FileDataSource(System.getProperty("user.home") + imagePath);

        // Attach the image to the MIME body part
        imageBodyPart.setDataHandler(new DataHandler(dataSource));

        // Set the Content-ID header to reference the image in the HTML email
        imageBodyPart.setHeader("Content-ID", "<image>");

        // Add the image body part to the multipart container
        multipart.addBodyPart(imageBodyPart);
    }
    }


