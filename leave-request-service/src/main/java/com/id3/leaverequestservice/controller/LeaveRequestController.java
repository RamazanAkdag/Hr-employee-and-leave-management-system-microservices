package com.id3.leaverequestservice.controller;

import com.id3.leaverequestservice.model.dto.AcceptRejectLeaveRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.entity.LeaveRequest;
import com.id3.leaverequestservice.service.ILeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-request")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final ILeaveRequestService leaveRequestService;
    @PostMapping
    public CreateLeaveRequestResponse createLeaveRequest(@RequestBody CreateLeaveRequestRequest request) {
        return leaveRequestService.createLeaveRequest(request);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PostMapping("/accept")
    public void acceptLeaveRequest(@RequestBody AcceptRejectLeaveRequest request) {
        leaveRequestService.acceptLeaveRequest(request.getLeaveRequestId());
    }


    @PostMapping("/reject")
    public void rejectLeaveRequest(@RequestBody AcceptRejectLeaveRequest request) {
        leaveRequestService.rejectLeaveRequest(request.getLeaveRequestId());
    }

    @GetMapping("/{userId}")
    public List<LeaveRequest> getLeaveRequestsByUserId(@PathVariable("userId") int userId) {
        return leaveRequestService.getLeaveRequestsByUserId(userId);
    }


    @PostMapping("/cancel")
    public void cancelLeaveRequest(@RequestBody AcceptRejectLeaveRequest request) {
        leaveRequestService.cancelLeaveRequest(request.getLeaveRequestId());
    }
}
