package com.elibrary.backend.modules.checkout.exception;

/**
 * Exception thrown when a user tries to renew a loan that has reached the maximum allowed renewals
 */
public class MaximumRenewalsReachedException extends RuntimeException {
    public MaximumRenewalsReachedException(String message) {
        super(message);
    }
}
