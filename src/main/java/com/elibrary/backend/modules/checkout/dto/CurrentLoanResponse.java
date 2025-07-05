package com.elibrary.backend.modules.checkout.dto;

import com.elibrary.backend.modules.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the current book loan details for a user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentLoanResponse {

    private Book book;

    private int daysLeft;
}
