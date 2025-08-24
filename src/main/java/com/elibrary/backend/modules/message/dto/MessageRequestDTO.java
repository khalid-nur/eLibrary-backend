package com.elibrary.backend.modules.message.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user request to create a new message
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Question is required")
    private String question;


}
