package com.BookVerse.BookVerse.feedback;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateFeedbackDTO {

    @NotEmpty(message = "Comment cannot be empty")
    @NotNull(message = "Comment cannot be null")
    @NotBlank(message = "Comment cannot be blank")
    private String comment;
    @Positive
    @Min(value = 1, message = "Rating must be greater than 0")
    @Max(value = 5, message = "Rating must be less than 6")
    private Double rating;
    @NotNull(message = "User ID cannot be null")
    private Long bookId;

}
