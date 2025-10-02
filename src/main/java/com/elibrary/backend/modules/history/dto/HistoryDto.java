package com.elibrary.backend.modules.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representing a user's book history
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private Long id;

    private Long userId;

    private String userEmail;

    private Long bookId;

    private LocalDate checkoutDate;

    private LocalDate returnDate;

    private String title;

    private String author;

    private String description;

    private String img;
}
