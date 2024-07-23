package com.id3.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailRequest implements Serializable {

    private String to;
    private String subject;
    private String body;

}
