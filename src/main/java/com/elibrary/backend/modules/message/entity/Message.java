package com.elibrary.backend.modules.message.entity;

import com.elibrary.backend.modules.message.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a user message in the system
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "title")
    private String title;

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "response")
    private String response;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus messageStatus = MessageStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
