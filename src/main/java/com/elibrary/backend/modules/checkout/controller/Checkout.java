package com.elibrary.backend.modules.checkout.controller;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.checkout.dto.CheckoutCountDTO;
import com.elibrary.backend.modules.checkout.dto.CheckoutPerUserDTO;
import com.elibrary.backend.modules.checkout.dto.CurrentLoanResponse;
import com.elibrary.backend.modules.checkout.dto.LoanOverviewDTO;
import com.elibrary.backend.modules.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage book checkout requests
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/checkouts")
@Slf4j
public class Checkout {

    private final CheckoutService checkoutService;

    /**
     * Allows a user to check out a book
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book to check out
     * @return the checked out book
     */
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Book> checkoutBook(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(checkoutService.checkoutBookForUser(userEmail, bookId));

    }

    /**
     * Checks if a book is currently checked out by the user
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book
     * @return true if the book is checked out by the user
     */
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Boolean> isBookCheckedOutByUser(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(checkoutService.isBookCheckedOutByUser(userEmail, bookId));

    }

    /**
     * Fetches the number of books currently on loan by the user
     *
     * @param userDetails the authenticated user
     * @return number of current loans
     */
    @GetMapping("/loan-count")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Integer> getCurrentLoanCountForUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(checkoutService.getCurrentLoanCountForUser(userEmail));
    }

    /**
     * Fetches all current book loans for the user
     *
     * @param userDetails the authenticated user
     * @return list of current loans
     */
    @GetMapping("/current-loans")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<CurrentLoanResponse>> getCurrentLoansForUser(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(checkoutService.getCurrentLoansForUser(userEmail));

    }

    /**
     * Returns a book currently on loan by the user
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book to return
     * @return confirmation that the book has been returned
     */
    @PutMapping("/return")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Void> returnBook(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        checkoutService.returnBookForUser(userEmail, bookId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Renews a book loan for the user
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book to renew
     * @return confirmation that the book loan has been renewed
     */
    @PutMapping("/renew")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Void> renewBookLoan(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        checkoutService.renewBookLoanForUser(userEmail, bookId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Fetches the total number of books currently checked out by all users
     *
     * @return the total count of all checked-out books
     */
    @GetMapping("admin/checkout-counts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CheckoutCountDTO> getTotalCheckouts() {
        return ResponseEntity.ok(checkoutService.getTotalCheckouts());
    }

    /**
     * Fetches the number of books checked out by each user
     *
     * @return a list of users with their checkout counts
     */
    @GetMapping("admin/checkout-counts-per-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CheckoutPerUserDTO>> getUserCheckoutCounts() {
        return ResponseEntity.ok(checkoutService.getUserCheckoutCounts());
    }

    @GetMapping("/admin/all-checkouts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<LoanOverviewDTO>> adminGetAllCheckouts(Pageable pageable) {

        return ResponseEntity.ok(checkoutService.adminGetAllCheckouts(pageable));
    }

    /**
     * Allows an admin to renew a user's book loan
     *
     * @param userId the id of the user whose loan is being renewed
     * @param bookId the id of the book to renew
     * @return confirmation that the book loan has been renewed
     */
    @PutMapping("/admin/renew")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> adminRenewUserLoan(@RequestParam String userId, @RequestParam Long bookId) {
        checkoutService.adminRenewBookLoan(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Allows an admin to return a user's borrowed book
     *
     * @param userId the id of the user returning the book
     * @param bookId the id of the book being returned
     * @return confirmation that the book has been returned
     */
    @PutMapping("/admin/return")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> adminReturnUserBook(@RequestParam String userId, @RequestParam Long bookId) {
        checkoutService.adminReturnBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }
}
