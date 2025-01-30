package com.BookVerse.BookVerse.feedback;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.common.AuditFields;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private Double rating;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;

    @Embedded
    private AuditFields auditFields;
}
