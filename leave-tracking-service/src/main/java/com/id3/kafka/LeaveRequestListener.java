package com.id3.kafka;

import com.id3.LeaveRequestMessage;
import com.id3.job.MailJob;
import com.id3.model.MailRequest;
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

    @KafkaListener(id = "listener", topics = "leave-request-topic")
    public void listen(LeaveRequestMessage leaveRequestMessage){
        log.info("message : " + leaveRequestMessage);

        // E-posta gönderme görevlerini planlayın
        scheduleEmailJob(leaveRequestMessage, leaveRequestMessage.getLeaveStartDate(), "start");
        scheduleEmailJob(leaveRequestMessage, leaveRequestMessage.getLeaveEndDate(), "end");
    }

    private void scheduleEmailJob(LeaveRequestMessage message, Date sendDate, String emailType) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(sendDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date notificationDate = calendar.getTime();

        JobDataMap jobDataMap = new JobDataMap();
        MailRequest mailRequest = createMailRequest(message, emailType);
        jobDataMap.put("mailRequest", mailRequest);

        String jobIdentity = "emailJob-" + emailType + "-" + message.getMail() + "-" + UUID.randomUUID().toString();
        String triggerIdentity = "emailTrigger-" + emailType + "-" + message.getMail() + "-" + UUID.randomUUID().toString();

        JobDetail jobDetail = JobBuilder.newJob(MailJob.class)
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


    private MailRequest createMailRequest(LeaveRequestMessage message, String emailType) {
        String subject = "Leave Request Status Update";
        String body = createEmailBody(message, emailType);
        return new MailRequest(message.getMail(), subject, body);
    }

    private String createEmailBody(LeaveRequestMessage message, String emailType) {
        if (emailType.equals("start")) {
            return "Sayın " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                    "İzniniz " + message.getLeaveStartDate() + " tarihinde başlayacaktır. İyi tatiller dileriz.\n\n" +
                    "Best regards,\nHR Team";
        } else {
            return "Sayın " + message.getFirstName() + " " + message.getLastName() + ",\n\n" +
                    "İzniniz " + message.getLeaveEndDate() + " tarihinde sona erecektir.\n\n" +
                    "Best regards,\nHR Team";
        }
    }
}
