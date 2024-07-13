package com.id3.leaverequestservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLeaveRequestResponse {
    private int leaveRequestId;
    private int employeeId;
    private String status;
}
