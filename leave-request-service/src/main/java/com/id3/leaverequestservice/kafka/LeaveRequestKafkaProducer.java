package com.id3.leaverequestservice.kafka;

import com.id3.LeaveRequestMessage;
import com.id3.leaverequestservice.model.entity.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestKafkaProducer {

    private static final String TOPIC = "leave-request-topic";

    @Autowired
    private KafkaTemplate<String, LeaveRequestMessage> kafkaTemplate;

    public void sendLeaveRequestMessage(LeaveRequestMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }


}