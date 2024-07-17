package com.id3.kafka;

import com.id3.LeaveRequestMessage;
import com.id3.job.LeaveJob;
import com.id3.model.MailRequest;
import com.id3.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class LeaveRequestListener {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private IMailService mailService;

    @KafkaListener(id = "listener", topics = "leave-request-topic")
    public void listen(LeaveRequestMessage leaveRequestMessage){
        log.info("message : " + leaveRequestMessage);

        // Plan email jobs based on status
        if ("REJECTED".equalsIgnoreCase(leaveRequestMessage.getStatus())) {
            sendImmediateEmail(leaveRequestMessage, "rejected");
        } else if ("CANCELLED".equalsIgnoreCase(leaveRequestMessage.getStatus())) {
            sendImmediateEmail(leaveRequestMessage, "cancelled");
        } else {
            scheduleEmailJob(leaveRequestMessage, leaveRequestMessage.getLeaveStartDate(), "start");
            scheduleEmailJob(leaveRequestMessage, leaveRequestMessage.getLeaveEndDate(), "end");
        }
    }

    private void scheduleEmailJob(LeaveRequestMessage message, Date sendDate, String emailType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sendDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date notificationDate = calendar.getTime();

        JobDataMap jobDataMap = new JobDataMap();
        MailRequest mailRequest = createMailRequest(message, emailType);
        jobDataMap.put("mailRequest", mailRequest);
        jobDataMap.put("leaveRequestMessage", message);
        jobDataMap.put("emailType", emailType); // Add emailType to JobDataMap

        String jobIdentity = "emailJob-" + emailType + "-" + message.getMail() + "-" + UUID.randomUUID().toString();
        String triggerIdentity = "emailTrigger-" + emailType + "-" + message.getMail() + "-" + UUID.randomUUID().toString();

        JobDetail jobDetail = JobBuilder.newJob(LeaveJob.class)
                .withIdentity(jobIdentity)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerIdentity)
                .startAt(notificationDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Error scheduling job", e);
        }
    }

    private void sendImmediateEmail(LeaveRequestMessage message, String emailType) {
        MailRequest mailRequest = createMailRequest(message, emailType);
        log.info("Sending immediate email: " + mailRequest);
        mailService.sendToMailService(mailRequest);
    }

    private MailRequest createMailRequest(LeaveRequestMessage message, String emailType) {
        String subject;
        String body;

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
                        "Your leave request has been rejected.\n\n" +
                        "Best regards,\nHR Team";
                break;
            case "cancelled":
                subject = "Leave Request Cancelled";
                body = "Dear " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                        "Your leave request has been cancelled.\n\n" +
                        "Best regards,\nHR Team";
                break;
            default:
                throw new IllegalArgumentException("Unknown email type: " + emailType);
        }

        return new MailRequest(message.getMail(), subject, body);
    }
}
