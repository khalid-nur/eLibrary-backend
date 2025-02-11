package com.elibrary.backend.security;

import com.elibrary.backend.modules.auth.entity.User;
import com.elibrary.backend.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService to load user details from the database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Fetches the user details by email
     *
     * @param email the email address of the user
     * @return the UserDetails for the given email
     * @throws UsernameNotFoundException if no user is found for the email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email " + email));


        return user;
    }
}
