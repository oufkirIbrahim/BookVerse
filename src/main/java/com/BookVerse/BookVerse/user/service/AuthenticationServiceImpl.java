package com.BookVerse.BookVerse.user.service;

import com.BookVerse.BookVerse.email.EmailService;
import com.BookVerse.BookVerse.email.EmailTemplateName;
import com.BookVerse.BookVerse.security.JwtService;
import com.BookVerse.BookVerse.user.dto.AuthenticationRequest;
import com.BookVerse.BookVerse.user.dto.AuthenticationResponse;
import com.BookVerse.BookVerse.user.dto.RegisterRequest;
import com.BookVerse.BookVerse.user.entity.Token;
import com.BookVerse.BookVerse.user.entity.User;
import com.BookVerse.BookVerse.user.repository.RoleRepository;
import com.BookVerse.BookVerse.user.repository.TokenRepository;
import com.BookVerse.BookVerse.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mail.activation-Url}")
    private String activationUrl;

    @Override
    public void register(RegisterRequest registerRequest) throws MessagingException {
        var role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        // register user
        User newUser = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of(role))
                .accountLocked(false)
                .isEnabled(false)
                .build();
        userRepository.save(newUser);
        sendValidationEmail(newUser);

    }


    private void sendValidationEmail(User user) throws MessagingException {
        var token = generateValidationToken(user);
        emailService.sendConfirmationMail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                token.getToken(),
                "Activate your account"
        );
    }

    private Token generateValidationToken(User user) {
        String token = generateToken(6);
        Token validationToken = Token.builder()
                .token(token)
                .user(user)
                .createdDate(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();
        tokenRepository.save(validationToken);
        return validationToken;
    }

    private String generateToken(int size) {
        String code = "0123456789";
        StringBuilder token = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < size; i++) {
            int index = secureRandom.nextInt(code.length());
            token.append(code.charAt(index));
        }
        return token.toString();
    }

    @Transactional
    @Override
    public void activateAccount(String token){
        Token validationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Error: Token not found"));
        User user = userRepository.findById(validationToken.getUser().getId()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        user.setEnabled(true);
        userRepository.save(user);
        validationToken.setConfirmedDate(LocalDateTime.now());
        tokenRepository.save(validationToken);
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest authenticationRequest){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = (User) authentication.getPrincipal();
        Map<String, Object> claims = Map.of(
                "fullName", user.getFullName()
        );
        var jwt = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

}
