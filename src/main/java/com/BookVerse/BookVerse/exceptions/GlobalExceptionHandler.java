package com.BookVerse.BookVerse.exceptions;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleLockedException(LockedException ex) {
        return ResponseEntity.status(HttpStatus.LOCKED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(BusinessErrorCode.ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(BusinessErrorCode.ACCOUNT_LOCKED.getDescription())
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleDisabledException(DisabledException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(BusinessErrorCode.ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(BusinessErrorCode.ACCOUNT_DISABLED.getDescription())
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(BusinessErrorCode.INVALID_CREDENTIALS.getCode())
                        .businessErrorDescription(BusinessErrorCode.INVALID_CREDENTIALS.getDescription())
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleMessagingException(MessagingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Set<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .validationErrors(validationErrors)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        // Log the exception
        //todo: add logging
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error("Internal server error, please contact the administrator")
                        .build()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleOperationNotPermittedException(OperationNotPermittedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build()
        );
    }

}
