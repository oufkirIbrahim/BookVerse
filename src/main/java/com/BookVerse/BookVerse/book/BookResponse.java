package com.BookVerse.BookVerse.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String genre;
    private String cover;
    private String owner;
    private String isbn;
    private String language;
    private Integer pages;
    private Integer year;
    private Double rating;
}
