package com.elibrary.backend.modules.review.controller;

import com.elibrary.backend.modules.review.entity.Review;
import com.elibrary.backend.modules.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Review>> getReviewsByBookId(@PathVariable Long bookId, Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewsByBookId(bookId, pageable);

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


}
