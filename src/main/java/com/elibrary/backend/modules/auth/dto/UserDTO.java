package com.elibrary.backend.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Contains user information for safe transfer within the application
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userId;

    private String email;

    private String name;

    private String password;

    private String role;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
