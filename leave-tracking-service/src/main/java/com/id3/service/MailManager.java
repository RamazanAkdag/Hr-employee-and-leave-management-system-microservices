package com.id3.service;

import com.id3.model.MailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailManager implements IMailService {
    private final RestTemplate restTemplate;
    private static final String MAIL_SERVICE_URL = "http://mail-service/mail/send-mail";
    @Override
    public void sendToMailService(MailRequest mailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MailRequest> request = new HttpEntity<>(mailRequest, headers);
        log.info("mail request body : " + mailRequest);

        restTemplate.postForObject(MAIL_SERVICE_URL, request, Void.class);
        log.info("Mail sent: {}", mailRequest);
    }
}
