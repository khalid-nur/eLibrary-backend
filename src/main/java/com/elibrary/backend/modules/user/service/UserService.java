package com.elibrary.backend.modules.user.service;

import com.elibrary.backend.modules.user.dto.AdminUpdateUserRequest;
import com.elibrary.backend.modules.user.dto.UserCountDTO;
import com.elibrary.backend.modules.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for handling user operations
 */
public interface UserService {

    /**
     * Fetches all users with pagination
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of users
     */
    Page<UserDTO> getAllUsers(Pageable pageable);

    /**
     * Gets the total count of users
     *
     * @return total number of users
     */
    UserCountDTO getUserCounts();

    /**
     * Updates user details by user id
     *
     * @param userId the id of the user to update
     * @param adminUpdateUserRequest  updated user details
     * @return updated user information
     */
    UserDTO updateUser(String userId, AdminUpdateUserRequest adminUpdateUserRequest);

    /**
     * Deletes a user by id
     *
     * @param userId the id of the user to delete
     * @return confirmation user has been deleted
     */
    void deleteUser(String userId);

}
