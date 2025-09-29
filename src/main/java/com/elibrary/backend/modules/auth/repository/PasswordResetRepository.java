package com.elibrary.backend.modules.auth.repository;

import com.elibrary.backend.modules.auth.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing password reset
 */
@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    /**
     * Finds a password reset token by its value
     *
     * @param token the unique value used to look up the password reset request
     * @return the matching password reset token if one exists, otherwise empty
     */
    Optional<PasswordReset> findByToken(String token);



}
