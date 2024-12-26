package com.elibrary.backend.modules.book.repository;

import com.elibrary.backend.modules.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Repository for managing book
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds books in the database where the title contains the given search string.
     * @param title The text to search for in book titles
     * @param pageable pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    Page<Book> findByTitleContaining(@Param("title") String title, Pageable pageable);

}
