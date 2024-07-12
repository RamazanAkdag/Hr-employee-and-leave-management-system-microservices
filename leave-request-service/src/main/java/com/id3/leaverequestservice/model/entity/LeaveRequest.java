package com.id3.leaverequestservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Entity
@Table(name = "t_leave_request")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id")
    private int id;

    @Column(name = "employee_id", nullable = false)
    private int employeeId;

    @Column(name = "leave_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date leaveStartDate;

    @Column(name = "leave_end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date leaveEndDate;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;


}