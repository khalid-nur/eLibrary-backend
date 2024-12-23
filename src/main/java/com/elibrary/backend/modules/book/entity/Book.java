package com.elibrary.backend.modules.book.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a book in the system
 */

@Table(name = "book")
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "copies")
    private int copies;

    @Column(name = "copies_available")
    private int copiesAvailable;

    @Column(name = "category")
    private String category;

    @Column(name = "img", columnDefinition = "MEDIUMBLOB")
    @Lob
    private String img;
}
