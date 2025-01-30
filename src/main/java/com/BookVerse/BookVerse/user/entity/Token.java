package com.BookVerse.BookVerse.user.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime createdDate;
    private LocalDateTime expiryDate;
    private LocalDateTime confirmedDate;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
