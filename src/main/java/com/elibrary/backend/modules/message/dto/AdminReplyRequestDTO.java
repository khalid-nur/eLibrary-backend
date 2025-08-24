package com.elibrary.backend.modules.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the admin reply request to a user message
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminReplyRequestDTO {

    @NotNull(message = "ID is required")
    private Long id;

    @NotBlank(message = "Response cannot be empty")
    private String response;
}
