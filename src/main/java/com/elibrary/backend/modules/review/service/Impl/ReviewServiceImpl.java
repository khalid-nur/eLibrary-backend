package com.elibrary.backend.modules.review.service.Impl;

import com.elibrary.backend.common.exceptions.DuplicateResourceException;
import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.book.repository.BookRepository;
import com.elibrary.backend.modules.review.dto.CreateReviewRequest;
import com.elibrary.backend.modules.review.entity.Review;
import com.elibrary.backend.modules.review.repository.ReviewRepository;
import com.elibrary.backend.modules.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for business logic involving reviews
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    /**
     * Fetches a paginated list of reviews for a specific book id
     *
     * @param bookId   the id of the book to fetch reviews for
     * @param pageable pagination information
     * @return paginated list of reviews for the specified book
     */
    @Override
    public Page<Review> getReviewsByBookId(Long bookId, Pageable pageable) {
        return reviewRepository.findByBookId(bookId, pageable);
    }

    /**
     * CChecks if the user has submitted a review for a book
     *
     * @param userEmail the email address of the user
     * @param bookId    the id of the book to check review status for
     * @return true if the user has reviewed the book; false otherwise
     */
    @Override
    public boolean isBookReviewedByUser(String userEmail, Long bookId) {
        Review existingReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);

        return existingReview != null;

    }

    /**
     * Fetches the average rating for a book
     *
     * @param bookId the id of the book
     * @return the average rating, if there are no reviews return 0.0
     */
    @Override
    public double getAverageRatingByBookId(Long bookId) {

        // Check if the book exists by its id, or throw an exception if not found
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundExceptions("The requested book could not be found");
        }

        // Find all reviews for the given book id
        List<Review> reviews = reviewRepository.findAllByBookId(bookId);

        // If no reviews found, return 0.0 as the average rating
        if (reviews.isEmpty()) {
            return 0.0;
        }

        // Add up all review ratings for the book
        double totalRating = reviews.stream()
                .mapToDouble(review -> review.getRating())
                .sum();

        // Find the average rating by dividing total by number of reviews
        double average = totalRating / reviews.size();

        // Round the average rating to one decimal place
        average = Math.round(average * 10.0) / 10.0;

        // Return the average rating, with a maximum allowed value of 5.0
        return Math.min(average, 5.0);
    }

    /**
     * Creates a new review for a book by a user
     *
     * @param userEmail     the email address of the user
     * @param reviewRequest the review data to be submitted for a book
     */
    @Override
    public void postReview(String userEmail, CreateReviewRequest reviewRequest) {

        // Check if the book ID exists, or throw an exception if not found
        if (!bookRepository.existsById(reviewRequest.getBookId())) {
            throw new ResourceNotFoundExceptions("The book provided for review does not exist");
        }

        // Check if a review by this user for the given book already exists
        Review existingReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

        // If a review already exists, throw a duplication exception
        if (existingReview != null) {
            throw new DuplicateResourceException("Review for this book already exists");
        }

        // Create a new review record
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);

        // Set the review description if it contains text, else set it null
        review.setReviewDescription(StringUtils.hasText(reviewRequest.getDescription())
                ? reviewRequest.getDescription()
                : null);

        // Set the current date as the review date
        review.setDate(LocalDate.now());

        // Save the new review to the database
        reviewRepository.save(review);
    }

}
