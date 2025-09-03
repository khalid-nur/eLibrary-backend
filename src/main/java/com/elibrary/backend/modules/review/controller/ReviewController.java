package com.elibrary.backend.modules.review.controller;

import com.elibrary.backend.modules.review.dto.CreateReviewRequest;
import com.elibrary.backend.modules.review.dto.ReviewDTO;
import com.elibrary.backend.modules.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
     * @param bookId   the id of the book to fetch reviews for
     * @param pageable pagination information
     * @return paginated list of reviews for the specified book
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByBookId(@PathVariable Long bookId, Pageable pageable) {
        Page<ReviewDTO> reviews = reviewService.getReviewsByBookId(bookId, pageable);

        return ResponseEntity.ok(reviews);
    }

    /**
     * Checks if the user has submitted a review for a book
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book to check review status for
     * @return true if the user has reviewed the book, false otherwise
     */
    @GetMapping("/book/status")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Boolean> isBookReviewedByUser(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestParam Long bookId) {
        boolean reviewed = reviewService.isBookReviewedByUser(userDetails.getUsername(), bookId);

        return ResponseEntity.ok(reviewed);
    }

    /**
     * Fetches the average rating for a book
     *
     * @param bookId the id of the book
     * @return the average rating of the book
     */
    @GetMapping("/book/{bookId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        double averageRating = reviewService.getAverageRatingByBookId(bookId);

        return ResponseEntity.ok(averageRating);
    }

    /**
     * Creates a new review for a book by a user
     *
     * @param userDetails   the authenticated user
     * @param reviewRequest the review data to be submitted for a book
     * @return a response with 201 status when successful
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Void> postReview(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody CreateReviewRequest reviewRequest) {
        reviewService.postReview(userDetails.getUsername(), reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
