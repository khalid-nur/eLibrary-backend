package com.elibrary.backend.modules.auth.service;

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
}
