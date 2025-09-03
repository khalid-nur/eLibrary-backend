package com.elibrary.backend.modules.user.controller;

import com.elibrary.backend.modules.user.dto.AdminUpdateUserRequest;
import com.elibrary.backend.modules.user.dto.UserCountDTO;
import com.elibrary.backend.modules.user.dto.UserDTO;
import com.elibrary.backend.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("admin/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Fetches all users with pagination
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of users
     */
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok( userService.getAllUsers(pageable));
    }

    /**
     * Gets the total count of users
     *
     * @return total number of users
     */
    @GetMapping("/stats")
    public ResponseEntity<UserCountDTO> getUserCount() {
        return ResponseEntity.ok(userService.getUserCounts());
    }

    /**
     * Updates user details by user id
     *
     * @param userId the id of the user to update
     * @param adminUpdateUserRequest  updated user details
     * @return updated user information
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody AdminUpdateUserRequest adminUpdateUserRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, adminUpdateUserRequest));
    }

    /**
     * Deletes a user by id
     *
     * @param userId the id of the user to delete
     * @return confirmation user has been deleted
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
