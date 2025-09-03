package com.elibrary.backend.modules.message.controller;

import com.elibrary.backend.modules.message.dto.AdminReplyRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageCountsDTO;
import com.elibrary.backend.modules.message.dto.MessageRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageResponseDTO;
import com.elibrary.backend.modules.message.enums.MessageStatus;
import com.elibrary.backend.modules.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to manage user messages
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
@Slf4j
public class MessageController {

    private final MessageService messageService;

    /**
     * Allows a user to create a message
     *
     * @param messageRequest the message request
     * @param userDetails    the authenticated user
     * @return created message details, status 201
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<MessageResponseDTO> createMessage(@Valid @RequestBody MessageRequestDTO messageRequest,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();

        messageService.createMessage(messageRequest, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Fetches all messages for the authenticated user
     *
     * @param userDetails the authenticated user
     * @param pageable    pagination info like page number and size
     * @return paginated list of user messages
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Page<MessageResponseDTO>> getUserMessages(@AuthenticationPrincipal UserDetails userDetails,
                                                         Pageable pageable) {
        String userEmail = userDetails.getUsername();

        Page<MessageResponseDTO> messages = messageService.getMessagesForUser(userEmail, pageable);

        return ResponseEntity.ok(messages);
    }

    /**
     * Fetches all messages for admin
     *
     * @param pageable pagination info like page number and size
     * @return paginated list of all messages
     */
    @GetMapping("admin/messages")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<MessageResponseDTO>> getAllMessages(Pageable pageable) {
        Page<MessageResponseDTO> messages = messageService.getAllMessages(pageable);
        return ResponseEntity.ok(messages);
    }

    /**
     * Fetches messages filtered by status for admin
     *
     * @param status   the status to filter messages
     * @param pageable pagination info like page number and size
     * @return paginated list of messages with the given status
     */
    @GetMapping("/admin/messages/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<MessageResponseDTO>> getMessagesByStatus(@RequestParam MessageStatus status,
                                                             Pageable pageable) {
        return ResponseEntity.ok(messageService.getMessagesByStatus(status, pageable));
    }

    /**
     * Fetches counts of messages by status for admin
     *
     * @return number of pending messages
     */
    @GetMapping("admin/message-counts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageCountsDTO> getMessageCountsByStatus() {
        return ResponseEntity.ok(messageService.getMessageCountsByStatus());
    }


    /**
     * Allows an admin to reply to a user message
     *
     * @param userDetails          the authenticated admin
     * @param adminReplyRequestDTO the message reply request
     * @return confirmation message has been replied to, status 200
     */
    @PutMapping("admin/message/reply")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> replyToUserMessage(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody AdminReplyRequestDTO adminReplyRequestDTO) {
        String adminEmail = userDetails.getUsername();

        messageService.replyToUserMessage(adminReplyRequestDTO, adminEmail);

        return ResponseEntity.ok().build();
    }

    /**
     * Allows an admin to delete a message
     *
     * @param messageId the id of the message to delete
     * @return confirmation message has been deleted, status 204
     */
    @DeleteMapping("admin/message/{messageId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long messageId) {
        messageService.deleteMessageById(messageId);
        return ResponseEntity.noContent().build();
    }

}
