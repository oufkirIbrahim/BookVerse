package com.BookVerse.BookVerse.email;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendConfirmationMail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String ConfirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException;
}
