package com.BookVerse.BookVerse.user.repository;

import com.BookVerse.BookVerse.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
