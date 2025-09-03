package com.elibrary.backend.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an admin request to update a user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateUserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Role is required")
    private String role;
}
