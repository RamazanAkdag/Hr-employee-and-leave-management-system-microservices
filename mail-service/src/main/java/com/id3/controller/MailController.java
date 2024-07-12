package com.id3.controller;

import com.id3.model.MailRequest;
import com.id3.model.MailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @PostMapping("/send-mail")
    public ResponseEntity<MailResponse> sendMail(@RequestBody MailRequest mailRequest){

        return ResponseEntity.ok(
                MailResponse.builder().
                        message("mail sent to " + mailRequest.getTo() + " successfully").
                        build()
        );
    }
}
