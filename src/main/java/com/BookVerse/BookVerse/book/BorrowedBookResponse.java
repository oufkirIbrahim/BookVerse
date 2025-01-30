package com.BookVerse.BookVerse.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BorrowedBookResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private boolean isReturned;
    private boolean isReturnApproved;
}
