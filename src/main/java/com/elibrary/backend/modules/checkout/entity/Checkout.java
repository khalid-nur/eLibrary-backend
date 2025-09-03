package com.elibrary.backend.modules.checkout.entity;


import com.elibrary.backend.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a book checkout in the system
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "checkout")
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "book_id")
    private Long bookId;
}

