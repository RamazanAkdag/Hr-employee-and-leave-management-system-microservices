/*package com.id3.leaverequestservice.repository;

import com.id3.leaverequestservice.model.entity.LeaveRequest;
import com.id3.leaverequestservice.model.entity.LeaveType;
import com.id3.leaverequestservice.model.entity.Status;
import com.id3.leaverequestservice.repository.ILeaveRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LeaveRequestServiceRepositoryTests {
    @Autowired
    ILeaveRequestRepository leaveRequestRepository;

    @Test
    public void leaveRequestRepository_save_returnsSavedLeaveRequest() {
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

        // Act
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        // Assert
        assertThat(savedLeaveRequest).isNotNull();
        assertThat(savedLeaveRequest.getId()).isGreaterThan(0);
    }

    @Test
    public void LeaveRequestRepository_getAll_returnsLeaveRequestArray() {
        // Arrange
        Calendar calendar = Calendar.getInstance();

        // Leave request 1: start date 5 days from now, end date 10 days from now
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveStartDate1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate1 = calendar.getTime();

        LeaveRequest leaveRequest1 = LeaveRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate1)
                .leaveEndDate(leaveEndDate1)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        // Leave request 2: start date 10 days from now, end date 15 days from now
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date leaveStartDate2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate2 = calendar.getTime();

        LeaveRequest leaveRequest2 = LeaveRequest.builder()
                .employeeId(456)
                .leaveStartDate(leaveStartDate2)
                .leaveEndDate(leaveEndDate2)
                .description("Medical leave")
                .leaveType(LeaveType.SICK)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        leaveRequestRepository.save(leaveRequest1);
        leaveRequestRepository.save(leaveRequest2);

        // Act
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();

        // Assert
        assertThat(leaveRequests).isNotNull();
        assertThat(leaveRequests.size()).isEqualTo(2);
        assertThat(leaveRequests).extracting("employeeId").contains(123, 456);
        assertThat(leaveRequests).extracting("description").contains("Family vacation", "Medical leave");
    }

    @Test
    public void LeaveRequestRepository_findById_returnsLeaveRequest(){
        //Arrange
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

        //Act
        LeaveRequest lr = leaveRequestRepository.save(leaveRequest);

        LeaveRequest lr2 = leaveRequestRepository.findById(lr.getId()).get();

        //Assert
        assertThat(lr2).isEqualTo(leaveRequest);
        assertThat(lr2.getId()).isGreaterThan(0);
    }

    @Test
    public void LeaveRequestRepository_findByEmployeeId_returnsEmployeesLeaveRequestList(){
        // Arrange
        Calendar calendar = Calendar.getInstance();

        // Leave request 1: start date 5 days from now, end date 10 days from now
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveStartDate1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate1 = calendar.getTime();

        LeaveRequest leaveRequest1 = LeaveRequest.builder()
                .employeeId(123)
                .leaveStartDate(leaveStartDate1)
                .leaveEndDate(leaveEndDate1)
                .description("Family vacation")
                .leaveType(LeaveType.ANNUAL)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        // Leave request 2: start date 10 days from now, end date 15 days from now
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date leaveStartDate2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date leaveEndDate2 = calendar.getTime();

        LeaveRequest leaveRequest2 = LeaveRequest.builder()
                .employeeId(456)
                .leaveStartDate(leaveStartDate2)
                .leaveEndDate(leaveEndDate2)
                .description("Medical leave")
                .leaveType(LeaveType.SICK)
                .status(Status.PENDING)
                .acceptCount(0)
                .build();

        leaveRequestRepository.save(leaveRequest1);
        leaveRequestRepository.save(leaveRequest2);


        //Act
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(456);

        //Assert
        assertThat(leaveRequests.size()).isEqualTo(1);
        assertThat(leaveRequests.get(0).getDescription()).isEqualTo("Medical leave");



    }
}

*/