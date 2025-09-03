package com.elibrary.backend.modules.review.service;

import com.elibrary.backend.modules.review.dto.CreateReviewRequest;
import com.elibrary.backend.modules.review.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for review operations
 */
public interface ReviewService {

    /**
     * Fetches a paginated list of reviews for a specific book id
     *
     * @param bookId   the id of the book to fetch reviews for
     * @param pageable pagination information
     * @return paginated list of reviews for the specified book
     */
    Page<ReviewDTO> getReviewsByBookId(Long bookId, Pageable pageable);

    /**
     * Checks if the user has submitted a review for a book
     *
     * @param userEmail the email address of the user
     * @param bookId    the id of the book to check review status for
     * @return true if the user has reviewed the book; false otherwise
     */
    boolean isBookReviewedByUser(String userEmail, Long bookId);

    /**
     * Fetches the average rating for a specific book
     *
     * @param bookId the id of the book
     * @return the average rating, or 0.0 if there are no reviews
     */
    double getAverageRatingByBookId(Long bookId);

    /**
     * Creates a new review for a book by a user
     *
     * @param userEmail     the email address of the user
     * @param reviewRequest the review data including rating, book ID, and optional description
     */
    void postReview(String userEmail, CreateReviewRequest reviewRequest);

}
