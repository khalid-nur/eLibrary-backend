package com.elibrary.backend.modules.message.repository;

import com.elibrary.backend.modules.message.entity.Message;
import com.elibrary.backend.modules.message.enums.MessageStatus;
import com.elibrary.backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing user messages
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds messages in the database by user email
     *
     * @param user the email of the user
     * @param pageable pagination information
     * @return page of messages for the user
     */
    Page<Message> findByUser(User user, Pageable pageable);

    /**
     * Finds messages in the database by status
     *
     * @param messageStatus the status of the message
     * @param pageable pagination information
     * @return page of messages with the given status
     */
    Page<Message> findByMessageStatus(MessageStatus messageStatus, Pageable pageable);

    /**
     * Counts messages in the database by status
     *
     * @param messageStatus the status of the message
     * @return number of messages with the given status
     */
    long countByMessageStatus(MessageStatus messageStatus);


}
