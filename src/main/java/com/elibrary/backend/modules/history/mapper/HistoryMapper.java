package com.elibrary.backend.modules.history.mapper;

import com.elibrary.backend.modules.history.dto.HistoryDto;
import com.elibrary.backend.modules.history.entity.History;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between History entities and History DTOs
 */
@Component
@RequiredArgsConstructor
public class HistoryMapper {

    private final ModelMapper modelMapper;

    /**
     * Converts History entity to HistoryDto
     */
    public HistoryDto toHistoryDtoFromHistory(History history) {
        return modelMapper.map(history, HistoryDto.class);
    }


}
