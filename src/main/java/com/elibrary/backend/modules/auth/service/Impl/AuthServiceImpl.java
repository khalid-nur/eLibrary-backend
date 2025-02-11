package com.elibrary.backend.modules.auth.service.Impl;

import com.elibrary.backend.modules.auth.dto.UserDTO;
import com.elibrary.backend.modules.auth.entity.User;
import com.elibrary.backend.modules.auth.exception.UserAlreadyExistsException;
import com.elibrary.backend.modules.auth.mapper.UserMapper;
import com.elibrary.backend.modules.auth.repository.UserRepository;
import com.elibrary.backend.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for business logic handling user authentication
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    /**
     * Registers a new user from registration details
     *
     * @param userDTO Contains registration details to be saved to the database
     * @return A UserDTO containing the saved user’s details, including the UUID generated user id
     */
    @Override
    public UserDTO signUp(UserDTO userDTO) {
        // Check if email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        // Convert userDTO to User entity for database
        User user = userMapper.toUserFromDTO(userDTO);

        // Generate unique UUID for the new user
        user.setUserId(UUID.randomUUID().toString());

        // Encode the password
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Set default role to USER if no role is provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // Save the user to the database
        user = userRepository.save(user);

        // Convert the saved User entity back to a UserDTO for the response
        return userMapper.toUserDTOFromUser(user);
    }
}
