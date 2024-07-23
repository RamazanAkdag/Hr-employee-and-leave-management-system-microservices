package com.id3.leaverequestservice.repository;

import com.id3.leaverequestservice.model.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILeaveRequestRepository extends JpaRepository<LeaveRequest,Integer> {
    List<LeaveRequest> findByEmployeeId(int userId);
}
