package com.id3.leaverequestservice.model.dto;

import com.id3.leaverequestservice.model.entity.LeaveType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateLeaveRequestResponse {
    private int leaveRequestId;
    private int employeeId;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private String description;
    private LeaveType leaveType;
    private String status;
}
