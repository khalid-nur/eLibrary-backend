package com.elibrary.backend.modules.book.service.Impl;

import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.book.dto.BookCountDTO;
import com.elibrary.backend.modules.book.dto.BookRequestDTO;
import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.repository.BookRepository;
import com.elibrary.backend.modules.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for business logic involving book
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    /**
     * Fetches a paginated list of books from the database
     *
     * @param pageable pagination info
     * @return paginated list of books
     */
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Fetches a single book by its id
     *
     * @param id the id of the book to fetch
     * @return the book with the given id
     */
    @Override
    public Optional<Book> getBookById(Long id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new ResourceNotFoundExceptions("The requested book could not be found");

        }

        return book;
    }


    /**
     * Fetches a list of books by title with pagination
     *
     * @param title The text to search for in book titles
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    @Override
    public Page<Book> getBooksByTitle(String title, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContaining(title, pageable);
        if (books.isEmpty()) {
            throw new ResourceNotFoundExceptions("No books matching the given title were found");
        }
        return books;
    }

    /**
     * Fetches a list of books by category with pagination
     *
     * @param category The category to filter books by
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the given category
     */
    @Override
    public Page<Book> getBooksByCategory(String category, Pageable pageable) {
        Page<Book> books = bookRepository.findByCategory(category, pageable);
        if (books.isEmpty()) {
            throw new ResourceNotFoundExceptions("No books found in the specified category");
        }
        return books;
    }

    /**
     * Fetches the total number of books
     *
     * @return the total count of all books
     */
    @Override
    public BookCountDTO getBookCounts() {

        // Get the total count of all books
        long totalBooks = bookRepository.count();

        return new BookCountDTO(totalBooks);
    }

    /**
     * Creates a new book
     *
     * @param request a book request DTO containing the book details
     * @return the newly created book with all details saved in the database
     */
    @Override
    public Book createBook(BookRequestDTO request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setCopies(request.getCopies());
        book.setCopiesAvailable(request.getCopiesAvailable());
        book.setCategory(request.getCategory());
        book.setImg(request.getImg());

        return bookRepository.save(book);
    }
}


