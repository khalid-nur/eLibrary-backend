package com.elibrary.backend.modules.book.controller;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * Fetches a list of books where the title contains the given text
     * @param title The text to search for in book titles
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    @GetMapping("/search")
    public Page<Book> getBooksByTitle(@RequestParam String title, Pageable pageable) {
        return bookService.getBooksByTitle(title, pageable);
    }


    /**
     * Fetches a list of books by category with pagination
     * @param category The category to filter books by
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the given category
     */
    @GetMapping("/search/category")
    public Page<Book> getBooksByCategory(@RequestParam String category, Pageable pageable) {
        return bookService.getBooksByCategory(category, pageable);
    }

}
