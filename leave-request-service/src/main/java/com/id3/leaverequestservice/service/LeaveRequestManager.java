package com.id3.leaverequestservice.service;

import com.id3.LeaveRequestMessage;
import com.id3.leaverequestservice.kafka.LeaveRequestKafkaProducer;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.dto.PersonnelInfo;
import com.id3.leaverequestservice.model.entity.LeaveRequest;
import com.id3.leaverequestservice.model.entity.PersonnelInfoResponse;
import com.id3.leaverequestservice.model.entity.Status;
import com.id3.leaverequestservice.repository.ILeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestManager implements ILeaveRequestService{
    private final ILeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestKafkaProducer kafkaProducer;
    private final RestTemplate restTemplate;


    @Override
    public CreateLeaveRequestResponse createLeaveRequest(CreateLeaveRequestRequest request) {
        var leaveRequest = LeaveRequest.builder()
                .employeeId(request.getEmployeeId())
                .leaveStartDate(request.getLeaveStartDate())
                .leaveEndDate(request.getLeaveEndDate())
                .description(request.getDescription())
                .leaveType(request.getLeaveType())
                .status(Status.PENDING)
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        CreateLeaveRequestResponse response = CreateLeaveRequestResponse.builder()
                .leaveRequestId(savedLeaveRequest.getId())
                .employeeId(savedLeaveRequest.getEmployeeId())
                .status(savedLeaveRequest.getStatus().name())
                .build();

        return response;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest getById(int requestId) {
        return leaveRequestRepository.findById(requestId).get();
    }

    @Override
    public void acceptLeaveRequest(int leaveRequestId) {
        Optional<LeaveRequest> leaveRequestOpt = leaveRequestRepository.findById(leaveRequestId);
        if (leaveRequestOpt.isPresent()) {
            LeaveRequest leaveRequest = leaveRequestOpt.get();


            String personnelInfoUrl = "http://personnel-info-service/personnel-info/" + leaveRequest.getEmployeeId();
            PersonnelInfoResponse personnelInfo = restTemplate.getForObject(personnelInfoUrl, PersonnelInfoResponse.class);

            // if it hasnt manager. enough only 1 accept
            if (personnelInfo.getManagerId() == null) {
                leaveRequest.setStatus(Status.APPROVED);
                sendLeaveRequestToKafka(leaveRequest);
            } else {
                // if employee has manager.leave requires manager + hr accept
                leaveRequest.setAcceptCount(leaveRequest.getAcceptCount() + 1);
                if (leaveRequest.getAcceptCount() >= 2) {
                    leaveRequest.setStatus(Status.APPROVED);
                    sendLeaveRequestToKafka(leaveRequest);
                }
            }

            leaveRequestRepository.save(leaveRequest);
        }
    }

    @Override
    public void rejectLeaveRequest(int leaveRequestId) {
        Optional<LeaveRequest> leaveRequestOpt = leaveRequestRepository.findById(leaveRequestId);
        if (leaveRequestOpt.isPresent()) {
            LeaveRequest leaveRequest = leaveRequestOpt.get();
            leaveRequest.setStatus(Status.REJECTED);
            leaveRequestRepository.save(leaveRequest);

            // Gerekli bilgileri personnel-info-service'den al ve Kafka'ya yaz
            sendLeaveRequestToKafka(leaveRequest);
        }
    }

    @Override
    public void cancelLeaveRequest(int leaveRequestId) {
        Optional<LeaveRequest> leaveRequestOpt = leaveRequestRepository.findById(leaveRequestId);
        if (leaveRequestOpt.isPresent()) {
            LeaveRequest leaveRequest = leaveRequestOpt.get();
            leaveRequest.setStatus(Status.CANCELLED);
            leaveRequestRepository.save(leaveRequest);

            // Gerekli bilgileri personnel-info-service'den al ve Kafka'ya yaz
            sendLeaveRequestToKafka(leaveRequest);
        }
    }

    private void sendLeaveRequestToKafka(LeaveRequest leaveRequest) {
        // Employee bilgilerini personnel-info-service'den al
        String personnelInfoServiceUrl = "http://personnel-info-service/personnel-info/" + leaveRequest.getEmployeeId();
        PersonnelInfo personnelInfo = restTemplate.getForObject(personnelInfoServiceUrl, PersonnelInfo.class);

        // LeaveRequestMessage oluştur ve Kafka'ya gönder
        LeaveRequestMessage message = new LeaveRequestMessage();
        message.setFirstName(personnelInfo.getFirstName());
        message.setLastName(personnelInfo.getLastName());
        message.setMail(personnelInfo.getEmail());
        message.setManagerMail(personnelInfo.getManagerEmail());
        message.setLeaveStartDate(leaveRequest.getLeaveStartDate());
        message.setLeaveEndDate(leaveRequest.getLeaveEndDate());
        message.setDescription(leaveRequest.getDescription());
        message.setLeaveType(leaveRequest.getLeaveType().name());
        message.setStatus(leaveRequest.getStatus().name());

        kafkaProducer.sendLeaveRequestMessage(message);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByUserId(int userId) {
        return leaveRequestRepository.findByEmployeeId(userId);
    }


}
