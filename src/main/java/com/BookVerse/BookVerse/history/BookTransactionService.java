package com.BookVerse.BookVerse.history;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface BookTransactionService {
    Long requestBook(@Valid RequestBookDTO requestBookDTO, Authentication connectedUser);

    Long changeStatus(@Valid ChangeStatusDTO changeStatusDTO, Authentication connectedUser);

    Long returnBorrowedBook(Long bookId, Authentication connectedUser);

    Long approveReturnBorrowedBook(Long bookId, Authentication connectedUser);
}
