package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Book operations")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Long> saveBook(@RequestBody @Valid BookRequest bookRequest, Authentication connectedUser) {

        return ResponseEntity.ok(bookService.saveBook(bookRequest, connectedUser));
    }

    @GetMapping("/id/{book-id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable("book-id") Long bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/all")
    public PageResponse<BookResponse> findAllBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {

        return bookService.findAllBooks(page, size, connectedUser);
    }

    @GetMapping("/owner")
    public PageResponse<BookResponse> findBooksByOwner(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {

        return bookService.findBooksByOwner(page, size, connectedUser);
    }

    @GetMapping("/borrowed")
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {

        return bookService.findAllBorrowedBooks(page, size, connectedUser);
    }

    @GetMapping("/returned")
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {

        return bookService.findAllReturnedBooks(page, size, connectedUser);
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Long> updateBookShareable(@PathVariable("book-id") Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.updateBookShareable(bookId, connectedUser));
    }

    @PatchMapping("archive/{book-id}")
    public ResponseEntity<Long> archiveBook(@PathVariable("book-id") Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.updateArchiveStatus(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCover(@PathVariable("book-id") Long bookId,
                                             @RequestParam("file") MultipartFile cover,
                                             Authentication connectedUser
    ) {
        bookService.uploadBookCover(bookId, cover, connectedUser);
        return ResponseEntity.ok().build();
    }

}
