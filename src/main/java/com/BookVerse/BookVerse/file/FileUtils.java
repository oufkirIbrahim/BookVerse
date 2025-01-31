package com.BookVerse.BookVerse.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    public static byte[] readFileFromLocation(String location) {
        // read file from location
        if(location == null || location.isEmpty()) {
            return null;
        }
        Path filePath = new File(location).toPath();
        try {
            log.info("File read successfully: {}", location);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Could not read file: {}", e.getMessage());
        }
        return null;
    }
}
