package com.id3.model;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;
@Data
public class UpdateStatusRequest {
    private String mail;
    private String status;

}