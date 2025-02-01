package com.BookVerse.BookVerse.feedback;

import com.BookVerse.BookVerse.common.PageResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    Long createFeedback(@Valid CreateFeedbackDTO createFeedbackDTO, Authentication connectedUser);

    PageResponse<FeedbackResponseDTO> getFeedbacksByBookId(Long bookId, Integer page, Integer size, Authentication connectedUser);
}
