package com.BookVerse.BookVerse.feedback;

import com.BookVerse.BookVerse.common.PageResponse;
import com.BookVerse.BookVerse.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "owner", expression = "java(feedback.getUser().getId().equals(user.getId()))")
    FeedbackResponseDTO toDto(Feedback feedback, @Context User user);

    default List<FeedbackResponseDTO> toDtoList(List<Feedback> feedbacks, User user) {
        return feedbacks.stream()
                .map(feedback -> toDto(feedback, user))
                .collect(Collectors.toList());
    }

    default PageResponse<FeedbackResponseDTO> toPageResponse(Page<Feedback> feedbackPage, User user) {
        List<FeedbackResponseDTO> feedbackDtos = toDtoList(feedbackPage.getContent(), user);

        return PageResponse.<FeedbackResponseDTO>builder()
                .content(feedbackDtos)
                .totalPages(feedbackPage.getTotalPages())
                .totalElements(feedbackPage.getTotalElements())
                .currentPage(feedbackPage.getNumber())
                .pageSize(feedbackPage.getSize())
                .hasNext(feedbackPage.hasNext())
                .hasPrevious(feedbackPage.hasPrevious())
                .build();
    }

}
