package com.elibrary.backend.modules.book.service.Impl;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.repository.BookRepository;
import com.elibrary.backend.modules.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
     * @param pageable pagination info
     * @return paginated list of books
     */
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
