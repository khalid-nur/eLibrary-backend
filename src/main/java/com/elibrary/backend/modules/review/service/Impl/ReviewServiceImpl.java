package com.elibrary.backend.modules.review.service.Impl;

import com.elibrary.backend.modules.review.entity.Review;
import com.elibrary.backend.modules.review.repository.ReviewRepository;
import com.elibrary.backend.modules.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service implementation for business logic involving reviews
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * Fetches a paginated list of reviews for a specific book id
     *
     * @param bookId the id of the book to fetch reviews for
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
     * @param bookId   the id of the book to check review status for
     * @return true if the user has reviewed the book; false otherwise
     */
    @Override
    public boolean isBookReviewedByUser(String userEmail, Long bookId) {
        Review existingReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);

        return existingReview != null;

    }
}
