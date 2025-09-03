package com.elibrary.backend.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Represents a user in the system
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String id;

    private String userId;

    private String email;

    private String name;

    private String password;

    private String role;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
