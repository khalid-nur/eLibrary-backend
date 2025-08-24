package com.elibrary.backend.modules.message.mapper;

import com.elibrary.backend.modules.message.dto.MessageRequestDTO;
import com.elibrary.backend.modules.message.dto.MessageResponseDTO;
import com.elibrary.backend.modules.message.entity.Message;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between Message entity and DTOs
 */
@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final ModelMapper modelMapper;

    /**
     * Converts a Message entity to a response DTO
     *
     * @param message the Message entity
     * @return the MessageDTO
     */
    public MessageResponseDTO mapToResponseDTO(Message message) {
        return modelMapper.map(message, MessageResponseDTO.class);
    }


    /**
     * Converts a request DTO to a Message entity
     *
     * @param messageDTO the MessageDTO
     * @return the Message entity
     */
    public Message mapToEntity(MessageRequestDTO messageDTO) {
        return modelMapper.map(messageDTO, Message.class);
    }
}
