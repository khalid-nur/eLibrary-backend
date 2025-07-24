package com.elibrary.backend.security;

import com.elibrary.backend.modules.auth.service.TokenBlacklistService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filters incoming requests to validate JWT tokens
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * Processes each request to validate the JWT token and set the authentication context
     *
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the JWT token from the Authorization header
        String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String email = null;

        // Check if the token is present and starts with "Bearer"
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            // Extract the token
            jwtToken = requestTokenHeader.substring(7);

            // If the token is stored as no longer accepted, reject the request
            if(tokenBlacklistService.isTokenBlacklisted(jwtToken)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            try {
                // Extract the email from the JWT token
                email = jwtTokenProvider.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException ex) {
                // If the token is invalid or can't be read, throw an error
                throw new RuntimeException("Unable to get jwt token");
            }catch (ExpiredJwtException ex) {
                // If the token is expired, throw an error
                throw new RuntimeException("Jwt token has expired");
            }
        }

        // If the email is extracted and no authentication is set in the context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details using the email
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // Check if the token is valid for this user
            if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                // Create an authentication token and set it up in the security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass the request and response to the filter in the chain
        filterChain.doFilter(request, response);
    }
}
