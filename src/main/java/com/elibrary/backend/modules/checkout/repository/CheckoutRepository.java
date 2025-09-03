package com.elibrary.backend.modules.checkout.repository;

import com.elibrary.backend.modules.checkout.entity.Checkout;
import com.elibrary.backend.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing checkout
 */
@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    /**
     * Finds a checkout in the database by user email and book id
     *
     * @param user the email of the user
     * @param bookId    the id of the book
     * @return the matching checkout, or null if not found
     */
    Checkout findByUserAndBookId(User user, Long bookId);

    /**
     * Finds all checkouts in the database for a specific user
     *
     * @param user the email of the user
     * @return list of checkouts associated with the user
     */
    List<Checkout> findBooksByUser(User user);

}
