package com.elibrary.backend.modules.book.controller;

import com.elibrary.backend.modules.book.entity.Book;
import com.elibrary.backend.modules.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @param pageable pagination information
     * @return paginated list of books
     */
    @GetMapping
    public ResponseEntity<Page<Book>> getBooks (Pageable pageable){
        Page<Book> books = bookService.getBooks(pageable);

        return ResponseEntity.ok(books);

    }
}
