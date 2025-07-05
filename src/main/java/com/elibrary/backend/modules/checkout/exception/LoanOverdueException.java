package com.elibrary.backend.modules.checkout.exception;

/**
 * Exception thrown when a user attempts to renew a loan that is already overdue
 */
public class LoanOverdueException extends RuntimeException {
    public LoanOverdueException(String message) {
        super(message);
    }
}
