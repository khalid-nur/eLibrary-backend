package com.elibrary.backend.modules.user.service.Impl;

import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.user.dto.AdminUpdateUserRequest;
import com.elibrary.backend.modules.user.dto.UserCountDTO;
import com.elibrary.backend.modules.user.dto.UserDTO;
import com.elibrary.backend.modules.user.entity.User;
import com.elibrary.backend.modules.user.mapper.UserMapper;
import com.elibrary.backend.modules.user.repository.UserRepository;
import com.elibrary.backend.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling all user business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    /**
     * Fetches all users with pagination
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of users
     */
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {

        // Find all users
        Page<User> users = userRepository.findAll(pageable);

        // Convert the list of user entities to user DTOs
        Page<UserDTO> userResponse = users.map(user -> {
            UserDTO userDTO = userMapper.toUserDTOFromUser(user);
            return userDTO;
        });

        // Return the paginated list of user DTOs
        return userResponse;
    }

    /**
     * Gets the total count of users
     *
     * @return total number of users
     */
    @Override
    public UserCountDTO getUserCounts() {

        // Count total users
        long totalUsers = userRepository.count();

        return new UserCountDTO(totalUsers);
    }

    /**
     * Updates user details by user id
     *
     * @param userId the id of the user to update
     * @param adminUpdateUserRequest  updated user details
     * @return updated user information
     */
    @Override
    public UserDTO updateUser(String userId, AdminUpdateUserRequest adminUpdateUserRequest) {

        // Find the user by their id, or throw an exception if not found
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));


        // Update the existing user's details with the new values
        user.setName(adminUpdateUserRequest.getName());
        user.setEmail(adminUpdateUserRequest.getEmail());
        user.setRole(adminUpdateUserRequest.getRole());

        // Save the updated user back to the database
        User saved = userRepository.save(user);

        // Convert entity to response DTO and return
        return userMapper.toUserDTOFromUser(saved);
    }

    /**
     * Deletes a user by id
     *
     * @param userId the id of the user to delete
     * @return confirmation user has been deleted
     */
    @Override
    public void deleteUser(String userId) {

        // Find the user by their id, or throw an exception if not found
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Delete the user from the database
        userRepository.delete(user);
    }

}
