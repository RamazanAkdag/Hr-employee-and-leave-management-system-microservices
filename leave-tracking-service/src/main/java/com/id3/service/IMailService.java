package com.id3.service;



import com.id3.leaverequestservice.model.LeaveRequestMessage;
import com.id3.model.MailRequest;

public interface IMailService {

    public void sendToMailService(MailRequest mailRequest);
    public MailRequest createMailRequest(LeaveRequestMessage message, String emailType);
}
