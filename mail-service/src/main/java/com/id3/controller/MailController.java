package com.id3.controller;

import com.id3.model.MailRequest;
import com.id3.model.MailResponse;
import com.id3.service.IMailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final IMailService mailService;

    @PostMapping("/send-mail")
    public ResponseEntity<MailResponse> sendMail(@RequestBody MailRequest mailRequest) throws MessagingException {
        mailService.sendSimpleMessage(mailRequest);
        return ResponseEntity.ok(
                MailResponse.builder().
                        message("mail sent to " + mailRequest.getTo() + " successfully").
                        build()
        );
    }
}
