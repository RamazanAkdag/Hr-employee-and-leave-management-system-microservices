package com.id3.service;

import com.id3.model.UpdateStatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class PersonnelDbUpdateManager implements IPersonnelDbUpdateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${personnel.info.service.url}")
    private String personnelInfoServiceUrl;

    @Override
    public void updateDbStatus(String employeeMail, String status) {
        String url = personnelInfoServiceUrl + "/personnel-info/update-status";

        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setEmail(employeeMail);
        request.setStatus(status);

        try {
            restTemplate.postForObject(url, request, Void.class);
            log.info("Successfully updated status for employeeId: " + employeeMail);
        } catch (Exception e) {
            log.error("Failed to update status for employeeId: " + employeeMail, e);
        }
    }
}