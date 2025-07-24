package com.elibrary.backend.modules.auth.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for managing tokens that should no longer be accepted for authentication
 */
@Service
public class TokenBlacklistService {

    // Set that holds tokens that should no longer be accepted
    private final Set<String> blacklist = new HashSet<>();

    /**
     * Adds the  JWT token to the blacklist, preventing future authentication
     *
     * @param token the JWT token to store
     */
    public void addTokenToBlacklist(String token){
        blacklist.add(token);
    }


    /**
     * Checks if the given token has been previously stored as invalid
     *
     * @param token the JWT token to check
     * @return true if the token has been invalidated, false otherwise
     */
    public boolean isTokenBlacklisted(String token ){
        return blacklist.contains(token);
    }
}
