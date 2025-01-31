package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.common.PageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Long saveBook(BookRequest bookRequest, Authentication authentication);

    BookResponse getBookById(Long bookId);

    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);

    PageResponse<BookResponse> findBooksByOwner(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser);

    Long updateBookShareable(Long bookId, Authentication connectedUser);

    Long updateArchiveStatus(Long bookId, Authentication connectedUser);

    void uploadBookCover(Long bookId, MultipartFile cover, Authentication connectedUser);
}
