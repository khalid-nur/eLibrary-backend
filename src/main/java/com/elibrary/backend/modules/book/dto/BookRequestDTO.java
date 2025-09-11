package com.elibrary.backend.modules.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an admin request to create a book
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Copies is required")
    @Min(value = 1, message = "Copies must be at least 1")
    private Integer copies;

    @NotNull(message = "Copies available is required")
    @Min(value = 0, message = "Copies available must be at least 0")
    private Integer copiesAvailable;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image URL is required")
    private String img;
}
