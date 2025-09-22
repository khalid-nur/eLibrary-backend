package com.elibrary.backend.modules.message.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Response cannot be empty")
    private String response;
}
