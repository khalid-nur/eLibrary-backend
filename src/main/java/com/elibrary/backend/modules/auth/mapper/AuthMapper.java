package com.elibrary.backend.modules.auth.mapper;

import com.elibrary.backend.modules.auth.dto.RegisterUserRequest;
import com.elibrary.backend.modules.auth.dto.RegisterUserResponse;
import com.elibrary.backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between user entities and user authentication
 */
@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final ModelMapper modelMapper;

    /**
     * Converts registration request to User entity
     */
    public User toUserFromRegisterRequest(RegisterUserRequest request) {
        return modelMapper.map(request, User.class);
    }

    /**
     * Converts User entity to registration response
     */
    public RegisterUserResponse toRegisterUserResponseFromUser(User user) {
        return modelMapper.map(user, RegisterUserResponse.class);
    }
}
