package com.BookVerse.BookVerse.history;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long>,
        JpaSpecificationExecutor<BookTransactionHistory> {

    @Query("SELECT bth FROM BookTransactionHistory bth WHERE bth.book.id = :bookId AND bth.user.id = :userId AND bth.status = :status")
    Optional<BookTransactionHistory> findByBookIdAndUserIdAndStatus(Long bookId, Long userId, Status status);
}
