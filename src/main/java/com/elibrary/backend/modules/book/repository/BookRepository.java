package com.elibrary.backend.modules.book.repository;

import com.elibrary.backend.modules.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for managing book
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds books in the database where the title contains the given search string
     * @param title The text to search for in book titles
     * @param pageable pagination info like page number and size
     * @return A paginated list of books matching the search text
     */
    Page<Book> findByTitleContaining(@Param("title") String title, Pageable pageable);

    /**
     * Fetches books from the database that belong to the specified category
     * @param category The category to filter books by
     * @param pageable Pagination info like page number and size
     * @return A paginated list of books matching the given category
     */
    Page<Book> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT o FROM Book o WHERE id IN :book_ids")
    List<Book> findBooksByBookIds(@Param("book_ids") List<Long> bookIds);
}
