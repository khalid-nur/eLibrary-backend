package com.elibrary.backend.modules.history.repository;

import com.elibrary.backend.modules.history.entity.History;
import com.elibrary.backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing History
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    /**
     * Finds all history associated with a user
     *
     * @param user the user whose history to retrieve
     * @param pageable pagination information such as page number and size
     * @return paginated list of history for the user
     */
    Page<History> findHistoryByUser(User user, Pageable pageable);
}
