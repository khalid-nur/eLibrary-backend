package com.elibrary.backend.modules.auth.service.Impl;

import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.auth.entity.PasswordReset;
import com.elibrary.backend.modules.auth.exception.InvalidCredentialsException;
import com.elibrary.backend.modules.auth.exception.PasswordResetTokenAlreadyUsedException;
import com.elibrary.backend.modules.auth.exception.PasswordResetTokenExpiredException;
import com.elibrary.backend.modules.auth.repository.PasswordResetRepository;
import com.elibrary.backend.modules.auth.service.PasswordResetService;
import com.elibrary.backend.modules.emailNotification.service.Impl.EmailServiceImpl;
import com.elibrary.backend.modules.user.entity.User;
import com.elibrary.backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Service implementation for business logic involving password resets
 */
@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;

    private final PasswordResetRepository passwordResetRepository;

    private final EmailServiceImpl emailService;

    private final PasswordEncoder passwordEncoder;

    @Value("${password.reset.token.expiry}")
    private long resetTokenExpiryMinutes;

    /**
     * Generates a password reset token for the user with the given email and sends it to their email address
     *
     * @param userEmail the email of the user requesting a password reset
     */
    @Override
    public void generatePasswordResetToken(String userEmail)  {
        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Unable to send reset link. Please check your email and try again"));

        // Generate a new unique token
        String token = UUID.randomUUID().toString();

        // Set the token's expiry date
        Instant expiryDate = Instant.now().plus(resetTokenExpiryMinutes, ChronoUnit.MINUTES);

        // Create a new PasswordReset entity and assign values
        PasswordReset resetToken = new PasswordReset();
        resetToken.setToken(token);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUser(user);

        // Save the new token in the database
        passwordResetRepository.save(resetToken);

        // Send the password reset email containing the token to the user
        emailService.sendPasswordResetEmail(user.getName(), user.getEmail(), token);

    }

    /**
     * Resets the password for a user using the provided reset token
     *
     * @param token the password reset token
     * @param newPassword the new password to set for the user
     */
    @Override
    public void resetPassword(String token, String newPassword) {

        // Find the password reset token using the provided token, or throw an exception if not found
        PasswordReset resetToken = passwordResetRepository.findByToken(token)
                .orElseThrow(()-> new InvalidCredentialsException("This password reset link is invalid. Please request a new link"));

        // Check if the token has already been used, or throw an exception
        if (resetToken.isUsed()){
            throw new PasswordResetTokenAlreadyUsedException("This password reset link has already been used. Please request a new link.");
        }

        // Check if the token has expired, or throw an exception
        if(resetToken.getExpiryDate().isBefore(Instant.now())){
            throw new PasswordResetTokenExpiredException("This password reset link has expired. Please request a new link.");
        }

        // Find the user associated with the token and update their password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        // Save the updated user
        userRepository.save(user);

        // Mark the token as used and save it
        resetToken.setUsed(true);
        passwordResetRepository.save(resetToken);
    }
}
