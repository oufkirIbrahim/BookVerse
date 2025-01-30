package com.BookVerse.BookVerse.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long>,
        JpaSpecificationExecutor<BookTransactionHistory> {

}
