package com.elibrary.backend.modules.checkout.service.Impl;

import com.elibrary.backend.common.exceptions.DuplicateResourceException;
import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.repository.BookRepository;
import com.elibrary.backend.modules.checkout.dto.CheckoutCountDTO;
import com.elibrary.backend.modules.checkout.dto.CheckoutPerUserDTO;
import com.elibrary.backend.modules.checkout.dto.CurrentLoanResponse;
import com.elibrary.backend.modules.checkout.entity.Checkout;
import com.elibrary.backend.modules.checkout.exception.LoanOverdueException;
import com.elibrary.backend.modules.checkout.repository.CheckoutRepository;
import com.elibrary.backend.modules.checkout.service.CheckoutService;
import com.elibrary.backend.modules.user.entity.User;
import com.elibrary.backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service implementation for handling all checkout business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final BookRepository bookRepository;

    private final CheckoutRepository checkoutRepository;

    private final UserRepository userRepository;

    private static final int MAX_LOAN_DAYS = 7;

    /**
     * Allows a user to check out a book
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book to check out
     * @return the details of the checked-out book
     */
    @Override
    public Book checkoutBookForUser(String userEmail, Long bookId) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Find the book by its id, or throw an exception if not found
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundExceptions("The requested book could not be found"));

        // Check if the book is already checked out by the user
        Checkout existingCheckout = checkoutRepository.findByUserAndBookId(user, bookId);

        // If a user has checked out throw an exception
        if (existingCheckout != null) {
            throw new DuplicateResourceException("Book already checked out by this user");
        }

        // Check if there are any copies of the book available for checkout
        if (book.getCopiesAvailable() <= 0) {
            throw new ResourceNotFoundExceptions("No copies available for checkout");
        }

        // Decrease the number of available copies for the book
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);

        // Save the updated book information back to the database
        bookRepository.save(book);

        // Create and save a new checkout record
        Checkout checkout = new Checkout();
        checkout.setUser(user);
        checkout.setCheckoutDate(LocalDate.now());
        checkout.setReturnDate(LocalDate.now().plusDays(MAX_LOAN_DAYS));
        checkout.setBookId(book.getId());

        // Save the new checkout to the database
        checkoutRepository.save(checkout);

        // Return the book the user checked out
        return book;
    }

    /**
     * Fetches whether a specific book is currently checked out by a given user
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book
     * @return true if the book is checked out by the user, false otherwise
     */
    @Override
    public boolean isBookCheckedOutByUser(String userEmail, Long bookId) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Check if the book is already checked out by the user
        Checkout existingCheckout = checkoutRepository.findByUserAndBookId(user, bookId);

        // If found, return true. If not, return false
        return existingCheckout != null;
    }

    /**
     * Fetches the total number of books a user currently has checked out
     *
     * @param userEmail the email of the user
     * @return the count of books on loan to the user
     */
    @Override
    public int getCurrentLoanCountForUser(String userEmail) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Find all checkout records for the user and return the total count
        return checkoutRepository.findBooksByUser(user).size();
    }

    /**
     * Fetches a list of current loans for a user, including book details and days left until due
     *
     * @param userEmail the email of the user
     * @return list of current loans
     */
    @Override
    public List<CurrentLoanResponse> getCurrentLoansForUser(String userEmail) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Create an empty list to store the details of the books currently on loan
        List<CurrentLoanResponse> currentLoanResponses = new ArrayList<>();

        // Get all the user's current checkouts
        List<Checkout> checkoutList = checkoutRepository.findBooksByUser(user);

        // Get the id of all books the user checked out
        List<Long> bookIdList = checkoutList.stream()
                .map(checkout -> checkout.getBookId())
                .collect(Collectors.toList());

        // Get all books corresponding to the extracted book id
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        // Get the current date
        LocalDate today = LocalDate.now();

        // Finds each book matching checkout record and determines the remaining days until its due date
        for (Book book : books) {
            checkoutList.stream()
                    .filter(checkout -> checkout.getBookId() == book.getId())
                    .findFirst()
                    .ifPresent(checkout -> {
                        LocalDate dueDate = checkout.getReturnDate();
                        long daysLeft = ChronoUnit.DAYS.between(today, dueDate);
                        currentLoanResponses.add(new CurrentLoanResponse(book, (int) daysLeft));
                    });
        }

        // Return the list of current loans
        return currentLoanResponses;
    }

    /**
     * Allows a user to return a previously borrowed book
     *
     * @param userEmail the email of the user returning the book
     * @param bookId    the id of the book being returned
     */
    @Override
    public void returnBookForUser(String userEmail, Long bookId) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Find the book by its id. Throw exceptions if not found
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("The requested book could not be found"));


        // Check if a checkout record exists for the user and book
        Checkout existingCheckout = checkoutRepository.findByUserAndBookId(user, bookId);

        // If no existing checkout record is found, throw an exception
        if (existingCheckout == null) {
            throw new ResourceNotFoundExceptions("This book is not currently checked out under this account");
        }

        // Increases available copies and saves the book
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        // Delete the checkout record from the database
        checkoutRepository.deleteById(existingCheckout.getId());
    }

    /**
     * Allows a user to extend the loan period for a borrowed book
     *
     * @param userEmail the email of the user
     * @param bookId    the id of the book to renew
     */
    @Override
    public void renewBookLoanForUser(String userEmail, Long bookId) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Check if a checkout record exists for the user and book
        Checkout existingCheckout = checkoutRepository.findByUserAndBookId(user, bookId);

        // If no existing checkout record is found, throw an exception
        if (existingCheckout == null) {
            throw new ResourceNotFoundExceptions("This book is not currently checked out under this account");
        }

        // Get the current return date of the loan
        LocalDate returnDate = (existingCheckout.getReturnDate());

        // Get the current date for comparison
        LocalDate today = LocalDate.now();

        // Check if the loan is overdue, if it is renewal isn't allowed
        if (returnDate.isBefore(today)) {
            throw new LoanOverdueException("Loan is overdue and cannot be renewed. Please return the book");
        }

        // Extend the loan's return date by the default period
        LocalDate newReturnDate = today.plusDays(MAX_LOAN_DAYS);

        // Update the return date on the existing checkout record
        existingCheckout.setReturnDate(newReturnDate);

        // Save the updated checkout record to the database
        checkoutRepository.save(existingCheckout);
    }

    /**
     * Fetches the total number of books currently checked out by all users
     *
     * @return the total count of all checked-out books
     */
    @Override
    public CheckoutCountDTO getTotalCheckouts() {

        // Get the total count of all checked out
        long totalCheckouts = checkoutRepository.count();

        return new CheckoutCountDTO(totalCheckouts);
    }

    /**
     * Fetches the number of books currently checked out by each user
     *
     * @return a list of objects containing the user ID, email and total number of checkouts
     */
    @Override
    public List<CheckoutPerUserDTO> getUserCheckoutCounts() {
        List<User> users = userRepository.findAll();

        List<CheckoutPerUserDTO> checkoutPerUser = users.stream().map(
                user -> {

                    String userId = user.getUserId();

                    String userEmail = user.getEmail();

                    long totalCheckouts = checkoutRepository.findBooksByUser(user).size();

                    return new CheckoutPerUserDTO(userId, userEmail, totalCheckouts);
                }).toList();

        return checkoutPerUser;
    }
}
