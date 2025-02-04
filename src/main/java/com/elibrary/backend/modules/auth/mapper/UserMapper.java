package com.elibrary.backend.modules.auth.mapper;

import com.elibrary.backend.modules.auth.dto.RegisterUserRequest;
import com.elibrary.backend.modules.auth.dto.RegisterUserResponse;
import com.elibrary.backend.modules.auth.dto.UserDTO;
import com.elibrary.backend.modules.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between user entities and DTOs
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    public final ModelMapper modelMapper;

    /**
     * Converts UserDTO to registration response
     */
    public RegisterUserResponse toRegisterUserResponseFromDTO(UserDTO userDTO){
        return modelMapper.map(userDTO, RegisterUserResponse.class);
    }

    /**
     * Converts registration request to UserDTO
     */
    public UserDTO toUserDTOFromRegisterRequest (RegisterUserRequest registerUserRequest){
      return modelMapper.map(registerUserRequest, UserDTO.class);
    }

    /**
     * Converts UserDTO to User entity for database
     */
    public User toUserFromDTO (UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Converts User entity to UserDTO
     */
    public UserDTO toUserDTOFromUser (User user){
        return modelMapper.map(user, UserDTO.class);
    }





}
