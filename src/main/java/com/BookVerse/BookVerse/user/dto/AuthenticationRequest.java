package com.BookVerse.BookVerse.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
}
