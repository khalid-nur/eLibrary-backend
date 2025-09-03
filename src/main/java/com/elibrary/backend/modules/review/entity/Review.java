package com.elibrary.backend.modules.review.entity;

import com.elibrary.backend.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

/**
 * Entity representing a review for a book in the system
 */
@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDate date;

    @Column(name = "rating")
    private double rating;

    @Column(name = "book_id")
    private Long bookId;

    @Lob
    @Column(name = "review_description", columnDefinition = "TEXT")
    private String reviewDescription;
}
