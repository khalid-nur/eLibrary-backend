package com.elibrary.backend.modules.history.service.Impl;

import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.history.dto.HistoryDto;
import com.elibrary.backend.modules.history.entity.History;
import com.elibrary.backend.modules.history.mapper.HistoryMapper;
import com.elibrary.backend.modules.history.repository.HistoryRepository;
import com.elibrary.backend.modules.history.service.HistoryService;
import com.elibrary.backend.modules.user.entity.User;
import com.elibrary.backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service implementation for handling history business logic
 */
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    private final UserRepository userRepository;

    private final HistoryMapper historyMapper;

    /**
     * Fetches a paginated list of book history for a user
     *
     * @param userEmail the email of the user whose history is being retrieved
     * @param pageable pagination information such as page number and size
     * @return paginated list of History for the user
     */
    @Override
    public Page<HistoryDto> getUserBookHistory(String userEmail, Pageable pageable) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Get the paginated list of history records for the user
        Page<History> histories = historyRepository.findHistoryByUser(user, pageable);

        // Convert each History entity to a HistoryDto
        Page<HistoryDto> historyResponse = histories.map(history -> {
            HistoryDto historyDto = historyMapper.toHistoryDtoFromHistory(history);
            return historyDto;
        });

        return historyResponse;

    }
}
