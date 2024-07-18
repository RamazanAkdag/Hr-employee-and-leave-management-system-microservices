package com.id3.service;

import com.id3.LeaveRequestMessage;
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

        try {
            restTemplate.postForObject(MAIL_SERVICE_URL, request, Void.class);
            log.info("Mail sent: {}", mailRequest);
        } catch (Exception e) {
            log.error("Failed to send mail", e);
        }
    }

    public MailRequest createMailRequest(LeaveRequestMessage message, String emailType) {
        String subject;
        String body;
        String dateRange = "from " + message.getLeaveStartDate() + " to " + message.getLeaveEndDate();

        switch (emailType) {
            case "start":
                subject = "Leave Request Starting Soon";
                body = "Dear " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                        "Your leave will start on " + message.getLeaveStartDate() + ". Have a great time!\n\n" +
                        "Best regards,\nHR Team";
                break;
            case "end":
                subject = "Leave Request Ending Soon";
                body = "Dear " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                        "Your leave will end on " + message.getLeaveEndDate() + ".\n\n" +
                        "Best regards,\nHR Team";
                break;
            case "rejected":
                subject = "Leave Request Rejected";
                body = "Dear " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                        "Your leave request for the period " + dateRange + " has been rejected.\n\n" +
                        "Best regards,\nHR Team";
                break;
            case "cancelled":
                subject = "Leave Request Cancelled";
                body = "Dear " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                        "Your leave request for the period " + dateRange + " has been cancelled.\n\n" +
                        "Best regards,\nHR Team";
                break;
            default:
                throw new IllegalArgumentException("Unknown email type: " + emailType);
        }

        return new MailRequest(message.getMail(), subject, body);
    }

}
