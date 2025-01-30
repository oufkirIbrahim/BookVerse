package com.BookVerse.BookVerse.user.controller;

import com.BookVerse.BookVerse.user.dto.AuthenticationRequest;
import com.BookVerse.BookVerse.user.dto.AuthenticationResponse;
import com.BookVerse.BookVerse.user.dto.RegisterRequest;
import com.BookVerse.BookVerse.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) throws MessagingException {
        // register user
        authenticationService.register(registerRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/activate_account")
    public void activateAccount(@RequestParam String token) {
        authenticationService.activateAccount(token);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
