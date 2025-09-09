package com.elibrary.backend.modules.checkout.dto;


/**
 * Representing the number of books checked out per user
 */
public record CheckoutPerUserDTO( String userId, String userEmail, long checkoutCount) { }
