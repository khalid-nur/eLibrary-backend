package com.elibrary.backend.modules.checkout.controller;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.checkout.dto.CheckoutCountDTO;
import com.elibrary.backend.modules.checkout.dto.CurrentLoanResponse;
import com.elibrary.backend.modules.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * @return confirmation message
     */
    @PutMapping("/return")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<String> returnBook(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        checkoutService.returnBookForUser(userEmail, bookId);
        return ResponseEntity.ok("Book returned successfully for user");
    }

    /**
     * Renews a book loan for the user
     *
     * @param userDetails the authenticated user
     * @param bookId      the id of the book to renew
     * @return confirmation message
     */
    @PutMapping("/renew")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<String> renewBookLoan(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookId) {
        String userEmail = userDetails.getUsername();

        checkoutService.renewBookLoanForUser(userEmail, bookId);
        return ResponseEntity.ok("Book loan renewed successfully.");
    }

    /**
     * Fetches the total number of books currently checked out by all users
     *
     * @return the total count of all checked-out books
     */    @GetMapping("admin/checkout-counts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CheckoutCountDTO> getTotalCheckouts() {
        return ResponseEntity.ok(checkoutService.getTotalCheckouts());
    }
}
