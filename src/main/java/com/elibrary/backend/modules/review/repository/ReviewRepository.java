package com.elibrary.backend.modules.review.repository;

import com.elibrary.backend.modules.review.entity.Review;
import com.elibrary.backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing reviews
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds reviews in the database for a specific book id
     *
     * @param bookId the id of the book to fetch reviews for
     * @param pageable pagination information
     * @return A paginated list of reviews for the specified book
     */
    Page<Review> findByBookId(Long bookId, Pageable pageable);


    /**
     * Finds a review submitted by a user for a book
     *
     * @param user the email of the user
     * @param bookId    the id of the book to check review status for
     * @return the matching review, or null if not found
     */
    Review findByUserAndBookId(User user, Long bookId);

    /**
     * Finds all reviews for a book id
     *
     * @param bookId the id of the book to fetch all reviews for
     * @return list of all reviews for the specified book
     */
    List<Review> findAllByBookId(Long bookId);
}
