package com.elibrary.backend.modules.checkout.dto;

import com.elibrary.backend.modules.checkout.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents the current book loan details for a user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanOverviewDTO {

    private Long id;

    private String userEmail;

    private String userName;

    private String userId;

    private Long bookId;

    private String bookTitle;

    private String bookAuthor;

    private LocalDate checkoutDate;

    private LocalDate returnDate;

    private LocalDate returnedDate;

    private LoanStatus status;

    private int remainingDays;

    private int renewalCount;

}
