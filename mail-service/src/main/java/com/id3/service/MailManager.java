package com.id3.service;

import com.id3.model.MailRequest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailManager implements IMailService{

    private final JavaMailSender javaMailSender;
    @Override
    public void sendSimpleMessage(MailRequest mailRequest) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("akdagramazan586@gmail.com");
        message.setTo(mailRequest.getTo());
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getBody());
        javaMailSender.send(message);

    }
}
