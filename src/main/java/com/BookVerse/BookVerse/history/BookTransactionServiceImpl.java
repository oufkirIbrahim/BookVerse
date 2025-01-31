package com.BookVerse.BookVerse.history;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.book.BookRepository;
import com.BookVerse.BookVerse.exceptions.OperationNotPermittedException;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTransactionServiceImpl implements BookTransactionService{

    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;

    @Override
    public Long requestBook(RequestBookDTO requestBookDTO, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(requestBookDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException(
                "No book found with id: " + requestBookDTO.getBookId()));
        if( book.getOwner().getId().equals(user.getId()) || book.isArchived() || !book.isShareable() ){
            throw new OperationNotPermittedException("You can't request your own book");
        }
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .book(book)
                .user(user)
                .message(requestBookDTO.getMessage())
                .status(Status.PENDING)
                .build();
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    @Override
    public Long changeStatus(ChangeStatusDTO changeStatusDTO, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findById(changeStatusDTO.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("No transaction found with id: " + changeStatusDTO.getTransactionId()));
        if( !bookTransactionHistory.getUser().getId().equals(user.getId()) ){
            throw new OperationNotPermittedException("You can't change status of a transaction that is not yours");
        }
        Status status = Status.valueOf(changeStatusDTO.getStatus().toUpperCase());
        if (status == Status.REJECTED){
            bookTransactionHistory.setStatus(status);
        }else{
            if( bookTransactionHistory.getBook().isBorrowed() ){
                throw new OperationNotPermittedException("Book is already borrowed");
            }else {
                bookTransactionHistory.setStatus(status);
                bookTransactionHistory.getBook().setBorrowed(status == Status.APPROVED);
            }
        }

        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Long returnBorrowedBook(Long bookId, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with id: " + bookId));
        if( book.getOwner().getId().equals(user.getId()) ){
            throw new OperationNotPermittedException("You can't borrow or return your own book");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository
                .findByBookIdAndUserIdAndStatus(book.getId(), user.getId(), Status.APPROVED)
                .orElseThrow(() -> new EntityNotFoundException("No transaction found for book with id: " + bookId));
        bookTransactionHistory.setStatus(Status.RETURNED);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Long approveReturnBorrowedBook(Long bookId, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with id: " + bookId));
        if( !book.getOwner().getId().equals(user.getId()) ){
            throw new OperationNotPermittedException("You can't approve return of a book that is not yours");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository
                .findByBookIdAndUserIdAndStatus(book.getId(), user.getId(), Status.RETURNED)
                .orElseThrow(() -> new EntityNotFoundException("No transaction found for book with id: " + bookId));
        bookTransactionHistory.setStatus(Status.RETURN_APPROVED);
        book.setBorrowed(false);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

}
