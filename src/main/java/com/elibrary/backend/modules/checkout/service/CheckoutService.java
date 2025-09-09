package com.elibrary.backend.modules.checkout.service;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.checkout.dto.CheckoutCountDTO;
import com.elibrary.backend.modules.checkout.dto.CheckoutPerUserDTO;
import com.elibrary.backend.modules.checkout.dto.CurrentLoanResponse;

import java.util.List;

/**
 * Service interface for handling all checkout operations
 */
public interface CheckoutService {

    /**
     * Allows a user to check out a book
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book to check out
     * @return the details of the checked-out book
     */
    Book checkoutBookForUser(String userEmail, Long bookId);

    /**
     * Checks if a specific book is currently checked out by a given user
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book
     * @return true if the book is checked out by the user, false otherwise
     */
    boolean isBookCheckedOutByUser(String userEmail, Long bookId);

    /**
     * Fetches the total number of books a user currently has checked out
     *
     * @param userEmail the email of the user
     * @return the count of books currently loaned to the user
     */
    int getCurrentLoanCountForUser(String userEmail);

    /**
     * Fetches a list of current loans for a user, including book details and days left until due
     *
     * @param userEmail the email of the user
     * @return a list of current loan responses
     */
    List<CurrentLoanResponse> getCurrentLoansForUser(String userEmail);

    /**
     * Allows a user to return a previously borrowed book
     *
     * @param userEmail the email of the user returning the book
     * @param bookId    the id of the book being returned
     */
    void returnBookForUser(String userEmail, Long bookId);

    /**
     * Allows a user to extend the loan period for a borrowed book
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book to renew
     */
    void renewBookLoanForUser(String userEmail, Long bookId);

    /**
     * Fetches the total number of books currently checked out by all users
     *
     * @return the total count of all checked-out books
     */
    CheckoutCountDTO getTotalCheckouts();

    /**
     * Fetches the number of books currently checked out by each user
     *
     * @return a list of objects containing the user ID, email and total number of checkouts
     */
    List<CheckoutPerUserDTO> getUserCheckoutCounts();


}
