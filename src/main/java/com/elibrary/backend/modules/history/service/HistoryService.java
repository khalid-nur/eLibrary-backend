package com.elibrary.backend.modules.history.service;

import com.elibrary.backend.modules.history.dto.HistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for handling history operations
 */
public interface HistoryService {

    /**
     * Fetches a paginated list of book history for a user
     *
     * @param userEmail the email of the user
     * @param pageable pagination information such as page number and size
     * @return paginated list of History for the user
     */
    Page<HistoryDto> getUserBookHistory(String userEmail, Pageable pageable);

}
