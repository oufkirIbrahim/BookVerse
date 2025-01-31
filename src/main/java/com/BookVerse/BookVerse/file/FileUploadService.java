package com.BookVerse.BookVerse.file;


import com.BookVerse.BookVerse.book.Book;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String saveFile(MultipartFile cover, Book book, Long userId);
}
