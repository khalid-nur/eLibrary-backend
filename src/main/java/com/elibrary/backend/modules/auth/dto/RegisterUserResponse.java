package com.elibrary.backend.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Response returned after successful user registration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserResponse {

    private String userId;

    private String email;

    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
