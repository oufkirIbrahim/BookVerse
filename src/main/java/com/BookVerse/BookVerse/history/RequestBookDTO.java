package com.BookVerse.BookVerse.history;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestBookDTO {
    @NotNull(message = "Book ID is required")
    @NotNull(message = "Book ID is required")
    private Long bookId;
    private String message;
}
