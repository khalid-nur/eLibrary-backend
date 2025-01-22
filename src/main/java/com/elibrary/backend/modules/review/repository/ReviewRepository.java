package com.elibrary.backend.modules.review.repository;

import com.elibrary.backend.modules.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
