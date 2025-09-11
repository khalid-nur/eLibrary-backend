package com.elibrary.backend.modules.book.controller;

import com.elibrary.backend.modules.book.dto.BookCountDTO;
import com.elibrary.backend.modules.book.dto.BookRequestDTO;
import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to manage book requests
 */
@RequiredArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {

    private final BookService bookService;

    /**
     * Fetches a list of books with pagination
     *
     * @param pageable pagination information
     * @return paginated list of books
     */
    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(Pageable pageable) {
        Page<Book> books = bookService.getBooks(pageable);

        return ResponseEntity.ok(books);
    }

    /**
     * Fetches a single book by its id
     *
     * @param id the id of the book to fetch
     * @return the book with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id).get();
        return ResponseEntity.ok(book);
    }

    /**
     * Fetches a list of books where the title contains the given text
     *
     * @param title The text to search for in book titles
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Book>> getBooksByTitle(@RequestParam String title, Pageable pageable) {
        Page<Book> books = bookService.getBooksByTitle(title, pageable);
        return ResponseEntity.ok(books);
    }


    /**
     * Fetches a list of books by category with pagination
     *
     * @param category The category to filter books by
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the given category
     */
    @GetMapping("/search/category")
    public ResponseEntity<Page<Book>> getBooksByCategory(@RequestParam String category, Pageable pageable) {
        Page<Book> books = bookService.getBooksByCategory(category, pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Fetches the total number of books currently checked out by all users
     *
     * @return the total count of all checked-out books
     */    @GetMapping("admin/book-counts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookCountDTO> getBookCounts() {
        return ResponseEntity.ok(bookService.getBookCounts());
    }


    /**
     * Creates a new book
     *
     * @param request book request DTO containing the book details
     * @return newly created book with all details
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDTO request) {
        Book savedBook = bookService.createBook(request);
        return ResponseEntity.ok(savedBook);
    }

}
