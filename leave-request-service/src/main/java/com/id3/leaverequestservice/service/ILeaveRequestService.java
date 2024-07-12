package com.id3.leaverequestservice.service;

import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.entity.LeaveRequest;

import java.util.List;



public interface ILeaveRequestService {


    CreateLeaveRequestResponse createLeaveRequest(CreateLeaveRequestRequest request);


    List<LeaveRequest> getAllLeaveRequests();


    void acceptLeaveRequest(int leaveRequestId);


    void rejectLeaveRequest(int leaveRequestId);


    List<LeaveRequest> getLeaveRequestsByUserId(int userId);


    void cancelLeaveRequest(int leaveRequestId);
}


