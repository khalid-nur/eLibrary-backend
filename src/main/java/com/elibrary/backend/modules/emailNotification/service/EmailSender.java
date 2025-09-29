package com.elibrary.backend.modules.emailNotification.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending email messages
 */
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    /**
     * Sends the given email message
     *
     * @param mimeMessage the email message to send
     */
    public void sendMail(MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }

}



