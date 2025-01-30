package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.common.PageResponse;
import org.springframework.security.core.Authentication;

public interface BookService {
    Long saveBook(BookRequest bookRequest, Authentication authentication);

    BookResponse getBookById(Long bookId);

    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);

    PageResponse<BookResponse> findBooksByOwner(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser);

    Long updateBookShareable(Long bookId, Authentication connectedUser);
}
