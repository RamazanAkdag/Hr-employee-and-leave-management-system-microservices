package com.id3.service;

import com.id3.model.MailRequest;
import jakarta.mail.MessagingException;

public interface IMailService {

    public void sendSimpleMessage(MailRequest mailRequest) throws MessagingException;

}