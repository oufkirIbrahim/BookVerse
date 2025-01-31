package com.BookVerse.BookVerse.file;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.user.entity.User;
import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${application.file.upload-dir}")
    private String baseUploadUrl;

    @Override
    public String saveFile(@Nonnull MultipartFile cover,@Nonnull Book book,@Nonnull Long userId) {
        var uploadPath = "users" + File.separator + userId + File.separator + "books" + File.separator + book.getId();
        return uploadFile(cover, uploadPath);
    }

    private String uploadFile(@Nonnull MultipartFile cover, @Nonnull String uploadPath) {
        var path = baseUploadUrl + File.separator + uploadPath;
        var uploadDir = new File(path);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                log.warn("Could not create directory: {}", uploadDir);
                return null;
            }
        }
        var fileExtension = getFileExtension(Objects.requireNonNull(cover.getOriginalFilename()));
        var fileName = System.currentTimeMillis() + fileExtension;
        var filePath = path + File.separator + fileName;
        Path destination = Path.of(filePath);
        try{
            Files.write(destination, cover.getBytes());
            log.info("File uploaded successfully: {}", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("Could not upload file: {}", e.getMessage());
        }
        return null;
    }

    private String getFileExtension(@Nonnull String originalFilename) {
        if (originalFilename.isEmpty()) {
            return "";
        }
        return originalFilename.lastIndexOf(".") != -1 ?
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
    }
}
