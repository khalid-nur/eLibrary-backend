package com.elibrary.backend.modules.book.service;

import com.elibrary.backend.modules.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for book operations
 */
public interface BookService {

    /**
     * Fetches a paginated list of books from the database
     * @param pageable pagination info
     * @return paginated list of books
     */
    Page<Book> getBooks(Pageable pageable);

}
