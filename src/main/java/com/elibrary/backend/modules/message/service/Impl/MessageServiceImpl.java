package com.elibrary.backend.modules.message.service.Impl;

import com.elibrary.backend.common.exceptions.DuplicateResourceException;
import com.elibrary.backend.common.exceptions.ResourceNotFoundExceptions;
import com.elibrary.backend.modules.message.dto.AdminReplyRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageCountsDTO;
import com.elibrary.backend.modules.message.dto.MessageRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageResponseDTO;
import com.elibrary.backend.modules.message.entity.Message;
import com.elibrary.backend.modules.message.enums.MessageStatus;
import com.elibrary.backend.modules.message.mapper.MessageMapper;
import com.elibrary.backend.modules.message.repository.MessageRepository;
import com.elibrary.backend.modules.message.service.MessageService;
import com.elibrary.backend.modules.user.entity.User;
import com.elibrary.backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service implementation for handling all message business logic
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final UserRepository userRepository;

    /**
     * Creates a new message for a user
     *
     * @param messageRequest the message details
     * @param userEmail      the email of the user creating the message
     * @return created message details
     */
    @Override
    public MessageResponseDTO createMessage(MessageRequestDTO messageRequest, String userEmail) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Convert the message request DTO into a message entity
        Message message = messageMapper.mapToEntity(messageRequest);

        // Set email, status, and timestamps on the message
        message.setUser(user);
        message.setMessageStatus(MessageStatus.PENDING);
        message.setCreatedAt(LocalDate.now());
        message.setUpdatedAt(LocalDate.now());

        // Save the message to the database
        message = messageRepository.save(message);

        // Convert entity to response DTO and return
        return messageMapper.mapToResponseDTO(message);
    }

    /**
     * Fetches all messages for a user
     *
     * @param userEmail the email of the user
     * @param pageable  pagination info like page number and size
     * @return paginated list of user messages
     */
    @Override
    public Page<MessageResponseDTO> getMessagesForUser(String userEmail, Pageable pageable) {

        // Find the user by their email, or throw an exception if not found
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        // Find messages for the given user
        Page<Message> messages = messageRepository.findByUser(user, pageable);

        // Convert the list of message entities to message DTOs
        Page<MessageResponseDTO> messageResponse = messages.map(message -> {
            MessageResponseDTO messageDTO = messageMapper.mapToResponseDTO(message);
            return messageDTO;
        });

        // Return the paginated list of message DTOs
        return messageResponse;
    }

    /**
     * Fetches all messages in the system (admin only)
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of all messages
     */
    @Override
    public Page<MessageResponseDTO> getAllMessages(Pageable pageable) {

        // Find all messages
        Page<Message> messages = messageRepository.findAll(pageable);


        // Convert the list of message entities to message DTOs
        Page<MessageResponseDTO> messageResponse = messages.map(message -> {
            MessageResponseDTO messageDTO = messageMapper.mapToResponseDTO(message);
            return messageDTO;
        });

        // Return the paginated list of message DTOs
        return messageResponse;
    }

    /**
     * Fetches messages filtered by their status (admin only)
     *
     * @param messageStatus the status to filter messages by
     * @param pageable      pagination info like page number and size
     * @return paginated list of messages with the given status
     */
    @Override
    public Page<MessageResponseDTO> getMessagesByStatus(MessageStatus messageStatus, Pageable pageable) {

        // Find messages for the given status
        Page<Message> messages = messageRepository.findByMessageStatus(messageStatus, pageable);

        // Convert the list of message entities to message DTOs
        Page<MessageResponseDTO> messageResponse = messages.map(message -> {
            MessageResponseDTO messageDTO = messageMapper.mapToResponseDTO(message);
            return messageDTO;
        });

        // Return the paginated list of message DTOs
        return messageResponse;
    }

    /**
     * Counts the number of messages by status (admin only)
     *
     * @return number of pending messages
     */
    @Override
    public MessageCountsDTO getMessageCountsByStatus() {

        // Get number of pending messages
        MessageCountsDTO pendingMessagesCount = new MessageCountsDTO(messageRepository.countByMessageStatus
                (MessageStatus.PENDING));
        return pendingMessagesCount;
    }

    /**
     * Allows an admin to reply to a user's message
     *
     * @param adminReplyRequestDTO the reply details including message ID and response
     * @param adminEmail           the email of the admin replying
     * @return confirmation message has been replied to
     */
    @Override
    public void replyToUserMessage(AdminReplyRequestDTO adminReplyRequestDTO, String adminEmail) {

        // Find the message by its id
        Optional<Message> message = messageRepository.findById(adminReplyRequestDTO.getId());

        // If message is not found, throw exception
        if (message.isEmpty()) {
            throw new ResourceNotFoundExceptions("Message not found");
        }

        // If message has already been replied to, throw exception
        if (message.get().getMessageStatus() == MessageStatus.REPLIED) {
            throw new DuplicateResourceException("Message has already been responded");
        }

        // Update message with admin response details
        message.get().setAdminEmail(adminEmail);
        message.get().setResponse(adminReplyRequestDTO.getResponse());
        message.get().setMessageStatus(MessageStatus.REPLIED);
        message.get().setUpdatedAt(LocalDate.now());

        // Save the updated message
        messageRepository.save(message.get());

    }

    /**
     * Deletes a message by its ID (admin only)
     *
     * @param messageId the ID of the message to delete
     * @return confirmation message has been deleted
     */
    @Override
    public void deleteMessageById(Long messageId) {

        // Find the message by its id
        Optional<Message> message = messageRepository.findById(messageId);

        // If message is not found, throw exception
        if (message.isEmpty()) {
            throw new ResourceNotFoundExceptions("Message not found");
        }

        // Deletes the message from the database
        messageRepository.deleteById(messageId);
    }
}
