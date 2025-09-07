package com.elibrary.backend.modules.auth.service;

import com.elibrary.backend.modules.auth.dto.AuthRequest;
import com.elibrary.backend.modules.auth.dto.AuthResponse;
import com.elibrary.backend.modules.auth.dto.RegisterUserRequest;
import com.elibrary.backend.modules.auth.dto.RegisterUserResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface for user authentication operations
 */
public interface AuthService {

     /**
      * Creates a new user profile from registration data
      *
      * @param registerUserRequest User registration data
      * @return Newly registered user details
      */
     RegisterUserResponse signUp(RegisterUserRequest registerUserRequest);

     /**
      * Authenticates a user and JWT token upon successful login
      *
      * @param authRequest Authentication request
      * @return Authentication response with user details and JWT token
      */
     AuthResponse login (AuthRequest authRequest);

     /**
      * Logs out the user by invalidating their JWT token
      *
      * @param request HTTP request containing the Authorization header
      */
     public void logout(HttpServletRequest request);

}
