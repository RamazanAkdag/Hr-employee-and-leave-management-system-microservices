package com.id3.leaverequestservice.service;

import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.entity.LeaveRequest;
import com.id3.leaverequestservice.model.entity.LeaveType;
import com.id3.leaverequestservice.model.entity.Status;
import com.id3.leaverequestservice.repository.ILeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceServiceTests {

    @Mock
    private ILeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private LeaveRequestManager leaveRequestService;

    @Test
    public void LeaveRequestService_CreateLeaveRequest_ReturnsLeaveRequestDto(){
        // Arrange
        Calendar calendar = Calendar.getInstance();

        // Leave start date (5 days from now)
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveStartDate = calendar.getTime();

        // Leave end date (10 days from now)
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate = calendar.getTime();

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        CreateLeaveRequestRequest leaveRequestDto = CreateLeaveRequestRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .build();

        when(leaveRequestRepository.save(Mockito.any(LeaveRequest.class))).thenReturn(leaveRequest);

        CreateLeaveRequestResponse response = leaveRequestService.createLeaveRequest(leaveRequestDto);

        // Assert
        assertThat(response).isNotNull();


        // Verify
        Mockito.verify(leaveRequestRepository, Mockito.times(1)).save(Mockito.any(LeaveRequest.class));
    }

    @Test
    public void LeaveRequestService_getAll_ReturnsLeaveRequestList(){
        // Arrange
        Calendar calendar = Calendar.getInstance();

        // Leave start date (5 days from now)
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveStartDate = calendar.getTime();

        // Leave end date (10 days from now)
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate = calendar.getTime();

        LeaveRequest leaveRequest1 = LeaveRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        LeaveRequest leaveRequest2 = LeaveRequest.builder()
                .employeeId(124)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Medical leave")
                .leaveType(LeaveType.SICK)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        List<LeaveRequest> leaveRequests = Arrays.asList(leaveRequest1, leaveRequest2);

        when(leaveRequestRepository.findAll()).thenReturn(leaveRequests);

        // Act
        List<LeaveRequest> result = leaveRequestService.getAllLeaveRequests();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(leaveRequest1, leaveRequest2);
    }

    @Test
    public void LeaveRequestService_AcceptLeaveRequest_

}
