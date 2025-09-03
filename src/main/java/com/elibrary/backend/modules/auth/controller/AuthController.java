package com.elibrary.backend.modules.auth.controller;

import com.elibrary.backend.modules.auth.dto.*;
import com.elibrary.backend.modules.user.dto.UserDTO;
import com.elibrary.backend.modules.user.mapper.UserMapper;
import com.elibrary.backend.modules.auth.service.AuthService;
import com.elibrary.backend.security.CustomUserDetailsService;
import com.elibrary.backend.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserMapper userMapper;

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Registers a new user
     *
     * @param registerUserRequest User registration data
     * @return Newly registered user details with HTTP 201 status
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register (@Valid @RequestBody RegisterUserRequest registerUserRequest){

        // Convert the registration request to user data
        UserDTO userDTO = userMapper.toUserDTOFromRegisterRequest(registerUserRequest);

        // Create the user
        userDTO = authService.signUp(userDTO);

        // Get the response ready with the new user’s info
        RegisterUserResponse registerUserResponse = userMapper.toRegisterUserResponseFromDTO(userDTO);

        // Return the response with 201 HTTP status code
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserResponse);
    }

    /**
     * Authenticates a user and JWT token upon successful login
     *
     * @param authRequest Authentication request
     * @return Authentication response with user details and JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody AuthRequest authRequest) {

        // Process the login request
        AuthResponse response = authService.login(authRequest);

        // Return the response with a 200 OK status
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Logs out the user and invalidates their JWT token
     *
     * @param request HTTP request containing the Authorization header
     */
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        // Process the logout by invalidating the token
        authService.logout(request);
    }
}
