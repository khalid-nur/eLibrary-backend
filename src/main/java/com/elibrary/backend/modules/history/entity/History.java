package com.elibrary.backend.modules.history.entity;

import com.elibrary.backend.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a book checkout history
 */
@Entity
@Data
@Table(name = "history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    @Column(name = "returned_date")
    private LocalDate returnDate;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "img", columnDefinition = "MEDIUMBLOB")
    @Lob
    private String img;
}
