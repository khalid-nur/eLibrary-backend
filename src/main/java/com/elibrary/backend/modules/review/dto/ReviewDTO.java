package com.elibrary.backend.modules.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a review for a book
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;

    private double rating;

    private Long bookId;

    private String reviewDescription;

    private LocalDate date;

    private String userEmail;

    private String userName;
}


