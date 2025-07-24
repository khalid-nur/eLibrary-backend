package com.elibrary.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Component for managing JWT token operations
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.expiration}")
    private long tokenExpiration;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generates a JWT token for the provided user details
     *
     * @param userDetails the user details to include in the token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails){
        // A Map to store custom claims
        Map<String, Object> claims = new HashMap<>();

        // Add the user's roles to the claims
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims) // The custom claims
                .setSubject(userDetails.getUsername()) // Set the subject to username
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue date to current time
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // Set the expiration date
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Extracts the username from a JWT token
     *
     * @param jwtToken the JWT token to parse
     * @return the username extracted from the token
     */
    public String getUsernameFromToken(String jwtToken) {
        // Extract the username for the claim
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token
     *
     * @param token the JWT token to parse
     * @param claimResolver the function to resolve the desired claim
     * @return the resolved claim value
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        // Process the token and extract the claims
        final Claims claims = Jwts.parser()
                .setSigningKey(secret) // Verify the token with the secret key
                .parseClaimsJws(token) // Convert the token into a JWS object
                .getBody();
        return claimResolver.apply(claims);
    }


    /**
     * Validates a JWT token against the provided user details
     *
     * @param jwtToken the JWT token to validate
     * @param userDetails the user details to compare against
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        // Extract the email from the token
        final String email = getUsernameFromToken(jwtToken);

        // Check if the token's email matches the user's email and if the token is not expired
        return email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if a JWT token is expired
     *
     * @param jwtToken the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String jwtToken) {
        // Extract the expiration date from the token
        final Date expiration = getClaimFromToken(jwtToken, Claims::getExpiration);

        // Check if the token is expired by comparing the expiration date to the current time
        // Returns true if the token is expired, false otherwise
        return expiration.before(new Date());
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request
     *
     * @param request HTTP request containing the Authorization header
     * @return the JWT token string, or null if no valid token is found
     */
    public String extractJwtTokenFromRequest(HttpServletRequest request) {

        // Get the Authorization header value from the request
        String bearerToken = request.getHeader("Authorization");

        // Check if the header is not null and starts with "Bearer ", if it does return the token
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
