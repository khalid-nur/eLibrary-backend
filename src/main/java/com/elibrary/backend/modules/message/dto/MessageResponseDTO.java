package com.elibrary.backend.modules.message.dto;

import com.elibrary.backend.modules.message.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents the response returned for a message
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponseDTO {

    private String userEmail;

    private String title;

    private String question;

    private String adminEmail;

    private String response;

    private MessageStatus messageStatus;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
