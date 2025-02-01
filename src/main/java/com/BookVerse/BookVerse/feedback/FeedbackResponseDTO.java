package com.BookVerse.BookVerse.feedback;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackResponseDTO {

    private Long id;
    private String comment;
    private Double rating;
    private Long bookId;
    private Long userId;
    private boolean isOwner;
}
