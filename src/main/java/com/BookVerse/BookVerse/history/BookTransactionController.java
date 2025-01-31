package com.BookVerse.BookVerse.history;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book-transactions")
@RequiredArgsConstructor
@Tag(name = "BookTransactions", description = "Book transaction operations")
public class BookTransactionController {

    private final BookTransactionService BookTransactionService;

    @PostMapping("/request")
    public ResponseEntity<Long> requestBook(@RequestBody @Valid RequestBookDTO requestBookDTO, Authentication connectedUser) {
        return ResponseEntity.ok(BookTransactionService.requestBook(requestBookDTO, connectedUser));
    }

    @PatchMapping("/change-status")
    public ResponseEntity<Long> changeStatus(@RequestBody @Valid ChangeStatusDTO changeStatusDTO, Authentication connectedUser) {
        return ResponseEntity.ok(BookTransactionService.changeStatus(changeStatusDTO, connectedUser));
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<Long> returnBorrowedBook(@PathVariable Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(BookTransactionService.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("/return/approve/{bookId}")
    public ResponseEntity<Long> approveReturnBorrowedBook(@PathVariable Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(BookTransactionService.approveReturnBorrowedBook(bookId, connectedUser));
    }

}
