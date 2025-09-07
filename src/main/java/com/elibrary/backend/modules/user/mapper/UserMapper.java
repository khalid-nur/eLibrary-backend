package com.elibrary.backend.modules.user.mapper;

import com.elibrary.backend.modules.user.dto.UserDTO;
import com.elibrary.backend.modules.user.entity.User;
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
     * Converts UserDTO to User entity
     */
    public User toUserFromUserDTO (UserDTO userDTO){

        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Converts User entity to UserDTO
     */
    public UserDTO toUserDTOFromUser (User user){

        return modelMapper.map(user, UserDTO.class);
    }
}
