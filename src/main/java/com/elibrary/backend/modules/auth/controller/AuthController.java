package com.elibrary.backend.modules.auth.controller;

import com.elibrary.backend.modules.auth.dto.RegisterUserRequest;
import com.elibrary.backend.modules.auth.dto.RegisterUserResponse;
import com.elibrary.backend.modules.auth.dto.UserDTO;
import com.elibrary.backend.modules.auth.mapper.UserMapper;
import com.elibrary.backend.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserMapper userMapper;

    private final AuthService authService;

    /**
     * Registers a new user
     *
     * @param registerUserRequest User registration data
     * @return Newly registered user details with HTTP 201 status
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register (@Valid @RequestBody RegisterUserRequest registerUserRequest){

        log.info("API /register is being called {},", registerUserRequest);

        // Convert the registration request to user data
        UserDTO userDTO = userMapper.toUserDTOFromRegisterRequest(registerUserRequest);

        // Create the user
        userDTO = authService.signUp(userDTO);

        // Get the response ready with the new user’s info
        RegisterUserResponse registerUserResponse = userMapper.toRegisterUserResponseFromDTO(userDTO);

        // Return the response with 201 HTTP status code
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserResponse);
    }
}
