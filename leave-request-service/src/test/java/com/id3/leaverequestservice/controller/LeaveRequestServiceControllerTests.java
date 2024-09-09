/*package com.id3.leaverequestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestRequest;
import com.id3.leaverequestservice.model.dto.CreateLeaveRequestResponse;
import com.id3.leaverequestservice.model.entity.LeaveType;

import com.id3.leaverequestservice.service.LeaveRequestManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = LeaveRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaveRequestManager leaveRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateLeaveRequestRequest leaveRequestDto;
    private CreateLeaveRequestResponse leaveRequestResponse;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @BeforeEach
    void setUp() throws ParseException {
        // Define fixed dates for testing
        Date leaveStartDate = dateFormat.parse("2024-07-22T12:29:02.359+00:00");
        Date leaveEndDate = dateFormat.parse("2024-07-27T12:29:02.359+00:00");

        leaveRequestDto = CreateLeaveRequestRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .build();

        leaveRequestResponse = CreateLeaveRequestResponse.builder()
                .leaveRequestId(1)
                .employeeId(123)
                .leaveStartDate(leaveStartDate)
                .leaveEndDate(leaveEndDate)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .status("PENDING")
                .build();

        // Mock the service layer
        when(leaveRequestService.createLeaveRequest(any(CreateLeaveRequestRequest.class))).thenReturn(leaveRequestResponse);
    }

    @Test
    public void LeaveRequestController_CreateLeaveRequest_ReturnsOk() throws Exception {
        // Fixed date strings
        String leaveStartDateJson = "2024-07-22T12:29:02.359+00:00";
        String leaveEndDateJson = "2024-07-27T12:29:02.359+00:00";

        // Use ObjectMapper to serialize the response object to JSON string
        String expectedResponseJson = objectMapper.writeValueAsString(leaveRequestResponse);

        this.mockMvc.perform(post("/leave-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"employeeId\": 123, \"leaveStartDate\": \"" + leaveStartDateJson + "\", \"leaveEndDate\": \"" + leaveEndDateJson + "\", \"description\": \"Family vacation\", \"leaveType\": \"ANNUAL\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseJson));
    }
}
*/