package com.BookVerse.BookVerse.user.service;

import com.BookVerse.BookVerse.user.dto.AuthenticationRequest;
import com.BookVerse.BookVerse.user.dto.AuthenticationResponse;
import com.BookVerse.BookVerse.user.dto.RegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    void register(@Valid RegisterRequest registerRequest) throws MessagingException;

    void activateAccount(String token);

    AuthenticationResponse authenticate(@Valid AuthenticationRequest authenticationRequest);
}
