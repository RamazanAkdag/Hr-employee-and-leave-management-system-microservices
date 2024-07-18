package com.id3.leaverequestservice.service;

import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.entity.*;
import com.id3.leaverequestservice.repository.ILeaveRequestRepository;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceServiceTests {

    @Mock
    private ILeaveRequestRepository leaveRequestRepository;

    @Mock
    private RestTemplate restTemplate;

    @Spy
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

        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        CreateLeaveRequestResponse response = leaveRequestService.createLeaveRequest(leaveRequestDto);

        // Assert
        assertThat(response).isNotNull();


        // Verify
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
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
    public void LeaveRequestService_AcceptLeaveRequestWhenManagerIsNull_ReturnsLeaveRequest(){
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
        leaveRequest.setId(1);  // Mock ID after saving

        PersonnelInfoResponse personnelInfoResponse = PersonnelInfoResponse.builder()
                .personnelId(123)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(PersonnelRole.EMPLOYEE)
                .departmentName("Engineering")
                .status(PersonnelStatus.ACTIVE)
                .position("Junior Developer")
                .managerId(null)
                .build();

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(leaveRequest));
        when(restTemplate.getForObject(anyString(), eq(PersonnelInfoResponse.class)))
                .thenReturn(personnelInfoResponse);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        // Mock the sendLeaveRequestToKafka method
        doNothing().when(leaveRequestService).sendLeaveRequestToKafka(any(LeaveRequest.class));


        // Act
        LeaveRequest acceptedLeaveRequest = leaveRequestService.acceptLeaveRequest(1);

        // Assert
        assertThat(acceptedLeaveRequest).isNotNull();
        assertThat(acceptedLeaveRequest.getStatus()).isEqualTo(Status.APPROVED);

        verify(leaveRequestRepository, times(1)).findById(1);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(PersonnelInfoResponse.class));
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
    }

    @Test
    public void LeaveRequestService_AcceptLeaveRequestWhenManagerIsNotNull_ReturnsLeaveRequest() {
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
        leaveRequest.setId(1);  // Mock ID after saving

        PersonnelInfoResponse personnelInfoResponse = PersonnelInfoResponse.builder()
                .personnelId(123)
                .firstName("John")
                .lastName("Doe")
                .managerId(2)
                .email("john.doe@example.com")
                .role(PersonnelRole.EMPLOYEE)
                .departmentName("Engineering")
                .status(PersonnelStatus.ACTIVE)
                .position("Junior Developer")
                .build();

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(leaveRequest));
        when(restTemplate.getForObject(anyString(), eq(PersonnelInfoResponse.class)))
                .thenReturn(personnelInfoResponse);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        // Act
        LeaveRequest acceptedLeaveRequest = leaveRequestService.acceptLeaveRequest(1);

        // Assert
        assertThat(acceptedLeaveRequest).isNotNull();
        assertThat(acceptedLeaveRequest.getStatus()).isEqualTo(Status.PENDING);  // Status should be PENDING as only 1 accept is done
        assertThat(acceptedLeaveRequest.getAcceptCount()).isEqualTo(1);

        verify(leaveRequestRepository, times(1)).findById(1);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(PersonnelInfoResponse.class));
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
        verify(leaveRequestService, never()).sendLeaveRequestToKafka(any(LeaveRequest.class));
    }

    @Test
    public void LeaveRequestService_RejectLeaveRequest_ReturnsVoid(){
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
        leaveRequest.setId(1);

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(leaveRequest));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);
        doNothing().when(leaveRequestService).sendLeaveRequestToKafka(any(LeaveRequest.class));


        leaveRequestService.rejectLeaveRequest(1);


        assertThat(leaveRequest.getStatus()).isEqualTo(Status.REJECTED);


    }

}
