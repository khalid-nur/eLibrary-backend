package com.elibrary.backend.modules.message.service;

import com.elibrary.backend.modules.message.dto.AdminReplyRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageCountsDTO;
import com.elibrary.backend.modules.message.dto.MessageRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageResponseDTO;
import com.elibrary.backend.modules.message.entity.Message;
import com.elibrary.backend.modules.message.enums.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for handling all message operations
 */
public interface MessageService {

    /**
     * Creates a new message for a user
     *
     * @param messageRequest the message details
     * @param userEmail      the email of the user creating the message
     * @return created message details
     */
    MessageResponseDTO createMessage(MessageRequestDTO messageRequest, String userEmail);

    /**
     * Fetches all messages for a user
     *
     * @param userEmail the email of the user
     * @param pageable  pagination info like page number and size
     * @return paginated list of user messages
     */
    Page<Message> getMessagesForUser(String userEmail, Pageable pageable);

    /**
     * Fetches all messages in the system (admin only)
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of all messages
     */
    Page<Message> getAllMessages(Pageable pageable);

    /**
     * Fetches messages filtered by their status (admin only)
     *
     * @param messageStatus the status to filter messages by
     * @param pageable      pagination info like page number and size
     * @return paginated list of messages with the given status
     */
    Page<Message> getMessagesByStatus(MessageStatus messageStatus, Pageable pageable);

    /**
     * Counts the number of messages by status (admin only)
     *
     * @return number of pending messages
     */
    MessageCountsDTO getMessageCountsByStatus();

    /**
     * Allows an admin to reply to a user's message.
     *
     * @param adminReplyRequestDTO the reply details including message ID and response
     * @param adminEmail           the email of the admin replying
     */
    void replyToUserMessage(AdminReplyRequestDTO adminReplyRequestDTO , String adminEmail);

    /**
     * Deletes a message by its ID (admin only)
     *
     * @param messageId the ID of the message to delete
     */
    void deleteMessageById(Long messageId);
}
