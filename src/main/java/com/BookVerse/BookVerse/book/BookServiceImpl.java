package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.common.PageResponse;
import com.BookVerse.BookVerse.exceptions.OperationNotPermittedException;
import com.BookVerse.BookVerse.file.FileUploadService;
import com.BookVerse.BookVerse.history.BookTransactionHistory;
import com.BookVerse.BookVerse.history.BookTransactionHistoryRepository;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileUploadService fileUploadService;

    @Override
    public Long saveBook(BookRequest bookRequest, Authentication authentication) {
        Book book = bookMapper.toBook(bookRequest);
        book.setOwner((User) authentication.getPrincipal());
        return bookRepository.save(book).getId();
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(
                "No book found with id: " + bookId));
        return bookMapper.toBookResponse(book);
    }

    @Override
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayedBooks(user.getId(), pageable);
        return bookMapper.toPageResponse(books);
    }

    @Override
    public PageResponse<BookResponse> findBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        return bookMapper.toPageResponse(books);
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> bookTransactionHistories = bookTransactionHistoryRepository.
                findAll(BookSpecification.findBorrowedBooksByUserId(user.getId()), pageable);
        return bookMapper.toBorrowedBookPageResponse(bookTransactionHistories);
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> bookTransactionHistories = bookTransactionHistoryRepository.
                findAll(BookSpecification.findReturnedBooksByUserId(user.getId()), pageable);
        return bookMapper.toBorrowedBookPageResponse(bookTransactionHistories);
    }

    @Override
    public  Long updateBookShareable(Long bookId, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(
                "No book found with id: " + bookId));
        if (book.getOwner().getId().equals(user.getId())){
            book.setShareable(!book.isShareable());
            bookRepository.save(book);
            return book.getId();
        }
        throw new OperationNotPermittedException("You Cannot update book Shareable status " + bookId);
    }

    @Override
    public Long updateArchiveStatus(Long bookId, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(
                "No book found with id: " + bookId));
        if (book.getOwner().getId().equals(user.getId())){
            book.setArchived(!book.isArchived());
            bookRepository.save(book);
            return book.getId();
        }
        throw new OperationNotPermittedException("You Cannot update book Shareable status " + bookId);
    }

    @Override
    public void uploadBookCover(Long bookId, MultipartFile coverImage, Authentication connectedUser){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(
                "No book found with id: " + bookId));
        User user = (User) connectedUser.getPrincipal();
        String coverPath = fileUploadService.saveFile(coverImage, book, user.getId());
        book.setCover(coverPath);
        bookRepository.save(book);
    }


}
