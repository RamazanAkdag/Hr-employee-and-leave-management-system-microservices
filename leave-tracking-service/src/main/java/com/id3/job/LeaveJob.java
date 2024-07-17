package com.id3.job;


import com.id3.LeaveRequestMessage;
import com.id3.model.MailRequest;
import com.id3.service.IMailService;
import com.id3.service.IPersonnelDbUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LeaveJob implements Job {

    @Autowired
    private IMailService mailService;

    @Autowired
    private IPersonnelDbUpdateService personnelDbUpdateService;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MailRequest mailRequest = (MailRequest) jobDataMap.get("mailRequest");
        mailService.sendToMailService(mailRequest);

        LeaveRequestMessage leaveRequestMessage = (LeaveRequestMessage) jobDataMap.get("leaveRequestMessage");
        String emailType = jobDataMap.getString("emailType");

        if ("start".equals(emailType)) {
            personnelDbUpdateService.updateDbStatus(leaveRequestMessage.getMail(), "PERMISSION");
        } else if ("end".equals(emailType)) {
            personnelDbUpdateService.updateDbStatus(leaveRequestMessage.getMail(), "ACTIVE");
        }
    }
}

