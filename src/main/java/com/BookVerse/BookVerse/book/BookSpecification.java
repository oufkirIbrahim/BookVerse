package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.history.BookTransactionHistory;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> withOwnerId(Long userId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), userId);
    }

    public static Specification<BookTransactionHistory> findBorrowedBooksByUserId(Long userId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<BookTransactionHistory> findReturnedBooksByUserId(Long userId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("book").get("id"), userId);
    }

}
