package com.elibrary.backend.modules.review.controller;

import com.elibrary.backend.modules.review.entity.Review;
import com.elibrary.backend.modules.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to manage review requests
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Fetches a list of reviews for a specific book by its id
     *
     * @param bookId the id of the book to fetch reviews for
     * @param pageable pagination information
     * @return paginated list of reviews for the specified book
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<Review>> getReviewsByBookId(@PathVariable Long bookId, Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewsByBookId(bookId, pageable);

        return ResponseEntity.ok(reviews);
    }


}
