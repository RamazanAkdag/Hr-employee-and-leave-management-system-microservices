package com.id3.job;


import com.id3.model.MailRequest;
import com.id3.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailJob implements Job {
    @Autowired
    private IMailService mailService;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MailRequest mailRequest = (MailRequest) jobDataMap.get("mailRequest");
        mailService.sendToMailService(mailRequest);
    }
}
