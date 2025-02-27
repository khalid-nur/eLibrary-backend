package com.elibrary.backend.modules.auth.service;

import com.elibrary.backend.modules.auth.dto.AuthRequest;
import com.elibrary.backend.modules.auth.dto.AuthResponse;
import com.elibrary.backend.modules.auth.dto.UserDTO;

/**
 * Service interface for user authentication operations
 */
public interface AuthService {

     /**
      * Creates a new user profile from registration data
      *
      * @param userDTO User registration data
      * @return Newly registered user details
      */
     UserDTO signUp(UserDTO userDTO);

     /**
      * Authenticates a user and JWT token upon successful login
      *
      * @param authRequest Authentication request
      * @return Authentication response with user details and JWT token
      */
     AuthResponse login (AuthRequest authRequest);
}
