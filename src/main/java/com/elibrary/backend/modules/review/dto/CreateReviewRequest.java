package com.elibrary.backend.modules.review.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request data for submitting a book review
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewRequest {
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private double rating;

    private long bookId;

    private String description;
}
