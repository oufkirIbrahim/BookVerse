package com.BookVerse.BookVerse.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCode {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code"),
    USER_ALREADY_EXISTS(1001, HttpStatus.BAD_REQUEST, "User already exists"),
    USER_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "User not found"),
    INVALID_ACTIVATION_Token(1003, HttpStatus.BAD_REQUEST, "Invalid activation token"),
    INVALID_CREDENTIALS(1004, HttpStatus.BAD_REQUEST, "Login and / or password incorrect"),
    ACCOUNT_LOCKED(1005, HttpStatus.BAD_REQUEST, "Account locked"),
    ACCOUNT_DISABLED(1006, HttpStatus.BAD_REQUEST, "Account disabled"),
    INVALID_TOKEN(1007, HttpStatus.BAD_REQUEST, "Invalid token"),
    TOKEN_EXPIRED(1008, HttpStatus.BAD_REQUEST, "Token expired"),
    INVALID_REQUEST(1009, HttpStatus.BAD_REQUEST, "Invalid request")
    ;

    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    BusinessErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
