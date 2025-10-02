package com.elibrary.backend.modules.history.controller;

import com.elibrary.backend.modules.history.dto.HistoryDto;
import com.elibrary.backend.modules.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling book history operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@Slf4j
public class HistoryController {

    private final HistoryService historyService;

    /**
     * Fetches the book checkout history for the authenticated user with pagination
     *
     * @param userDetails the authenticated user's details
     * @param pageable pagination information such as page number and size
     * @return paginated list of history for the user
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public Page<HistoryDto> getUserHistoryBooks(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {

        String userEmail = userDetails.getUsername();

        return historyService.getUserBookHistory(userEmail, pageable);

    }
}
