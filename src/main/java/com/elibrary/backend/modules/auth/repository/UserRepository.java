package com.elibrary.backend.modules.auth.repository;

import com.elibrary.backend.modules.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing user
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Finds a user by their email address in the database
     *
     * @param email The email address of the user
     * @return Optional containing the User if found, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user with the given email already exists in the database
     *
     * @param email The email address of the user
     * @return True if a user with the given email exists, false if no such user is found
     */
    boolean existsByEmail(String email);

}
