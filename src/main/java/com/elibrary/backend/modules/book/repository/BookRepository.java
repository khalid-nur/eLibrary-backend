package com.elibrary.backend.modules.book.repository;

import com.elibrary.backend.modules.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for managing book
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
