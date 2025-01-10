package com.elibrary.backend.modules.book.service;

import com.elibrary.backend.modules.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for book operations
 */
public interface BookService {

    /**
     * Fetches a paginated list of books from the database
     *
     * @param pageable pagination info
     * @return paginated list of books
     */
    Page<Book> getBooks(Pageable pageable);

    /**
     * Fetches a single book by its id
     *
     * @param id the id of the book to fetch
     * @return the book with the given id
     */
    Optional<Book> getBookById(Long id);


    /**
     * Fetches a list of books by title with pagination
     *
     * @param title The text to search for in book titles
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    Page<Book> getBooksByTitle(String title, Pageable pageable);


    /**
     * Fetches a list of books by category with pagination
     *
     * @param category The category to filter books by
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the given category
     */
    Page<Book> getBooksByCategory(String category, Pageable pageable);


}
