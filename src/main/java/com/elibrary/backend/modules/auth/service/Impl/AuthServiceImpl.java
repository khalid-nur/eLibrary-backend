package com.elibrary.backend.modules.auth.service.Impl;

import com.elibrary.backend.modules.auth.dto.AuthRequest;
import com.elibrary.backend.modules.auth.dto.AuthResponse;
import com.elibrary.backend.modules.auth.dto.UserDTO;
import com.elibrary.backend.modules.auth.entity.User;
import com.elibrary.backend.modules.auth.exception.InvalidCredentialsException;
import com.elibrary.backend.modules.auth.exception.UserAlreadyExistsException;
import com.elibrary.backend.modules.auth.mapper.UserMapper;
import com.elibrary.backend.modules.auth.repository.UserRepository;
import com.elibrary.backend.modules.auth.service.AuthService;
import com.elibrary.backend.modules.auth.service.TokenBlacklistService;
import com.elibrary.backend.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final TokenBlacklistService tokenBlacklistService;


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

    /**
     * Authenticates a user and generates a JWT token upon successful login
     *
     * @param authRequest Authentication request
     * @return Authentication response with user details and JWT token
     */
    @Override
    public AuthResponse login(AuthRequest authRequest) {

        // Authenticate the user using the provided credentials
        Authentication authentication = authenticate(authRequest);

        // Extract the authenticated user's details
        User user = (User) authentication.getPrincipal();

        // Generate a JWT token for the authenticated user
        String token = jwtTokenProvider.generateToken(user);

        // Return the authentication response with user details and token
        return AuthResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .token(token)
                .build();

    }


    /**
     * Logs out the user by extracting the JWT token from the request and
     * preventing it from being used in future authenticated requests
     *
     * @param request HTTP request containing the Authorization header
     */
    @Override
    public void logout(HttpServletRequest request) {

        // Extract the JWT token from the Authorization header
        String token = jwtTokenProvider.extractJwtTokenFromRequest(request);

        // If a token is found, store it so it will no longer be accepted for authentication
        if (token != null && !token.isEmpty()) {
            tokenBlacklistService.addTokenToBlacklist(token);
        }
    }

    /**
     * Authenticates a user using the provided credentials
     *
     * @param authRequest Authentication request
     * @return Authentication object if successful
     */
    private Authentication authenticate(AuthRequest authRequest) {
        try {
            // Authenticate the user using the authentication manager to check if the email and password are valid
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            // If the credentials are wrong, throw an exception
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }


}
