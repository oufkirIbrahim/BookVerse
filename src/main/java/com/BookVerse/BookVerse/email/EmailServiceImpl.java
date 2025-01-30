package com.BookVerse.BookVerse.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${application.mail.from}")
    private String from;

    @Async
    public void sendConfirmationMail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String ConfirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName = emailTemplateName == null ? "activate-account" : emailTemplateName.getTemplateName();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>(
                Map.of("username", username, "confirmationUrl", ConfirmationUrl, "activationCode", activationCode)
        );

        Context context = new Context();
        context.setVariables(properties);

        String html = templateEngine.process(templateName, context);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(mimeMessage);

    }
}
