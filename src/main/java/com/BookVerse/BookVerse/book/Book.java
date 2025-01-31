package com.BookVerse.BookVerse.book;


import com.BookVerse.BookVerse.common.AuditFields;
import com.BookVerse.BookVerse.feedback.Feedback;
import com.BookVerse.BookVerse.history.BookTransactionHistory;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String isbn;
    private String cover;
    private String language;
    private String publisher;
    private Integer pages;
    private Integer year;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> bookTransactionHistories;
    private boolean archived;
    private boolean shareable;
    private boolean borrowed;
    @Embedded
    private AuditFields auditFields;

}
