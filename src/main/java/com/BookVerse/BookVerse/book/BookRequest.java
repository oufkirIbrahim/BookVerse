package com.BookVerse.BookVerse.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class BookRequest {

    private Long id;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String author;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String genre;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String description;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String isbn;
    private String language;
    private boolean shareable;

}
