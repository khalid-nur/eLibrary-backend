package com.elibrary.backend.modules.review.mapper;

import com.elibrary.backend.modules.review.dto.ReviewDTO;
import com.elibrary.backend.modules.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between reviews entities and DTOs
 */
@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final ModelMapper modelMapper;

    /**
     * Convert Review entity to ReviewDTO
     */
    public ReviewDTO toReviewDTOFromReview(Review review) {
        ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
        return dto;
    }

}
