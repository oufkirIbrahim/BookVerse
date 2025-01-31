package com.BookVerse.BookVerse.history;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTransactionHistory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

}
