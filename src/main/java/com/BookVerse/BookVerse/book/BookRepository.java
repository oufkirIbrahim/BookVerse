package com.BookVerse.BookVerse.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b WHERE b.archived = false AND b.shareable = true AND b.owner.id != :userId")
    Page<Book> findAllDisplayedBooks(Long userId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.owner.id = :id")
    Page<Book> findAllByOwnerId(Long id, Pageable pageable);
}
