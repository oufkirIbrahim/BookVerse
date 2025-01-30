package com.BookVerse.BookVerse.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotEmpty(message = "firstname is required")
    @NotBlank(message = "firstname is required")
    private String firstname;

    @NotEmpty(message = "lastname is required")
    @NotBlank(message = "lastname is required")
    private String lastname;

    @Email(message = "email is invalid")
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min=8, message = "password must be at least 8 characters")
    private String password;
}
