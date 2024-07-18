package com.id3.leaverequestservice.model.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PersonnelInfoResponse {

    private Integer personnelId;
    private String firstName;
    private String lastName;
    private String email;
    private PersonnelRole role;
    private Integer managerId;
    private List<PersonnelInfoResponse> subordinates;
    private Timestamp startedAt;
    private String departmentName;
    private PersonnelStatus status;
    private String position;


    // toString method
    @Override
    public String toString() {
        return "PersonnelInfoResponse{" +
                "personnelId=" + personnelId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", managerId=" + managerId +
                ", subordinates=" + (subordinates != null ? subordinates.stream()
                .map(personnel -> "PersonnelInfo{" +
                        "personnelId=" + personnel.getPersonnelId() +
                        ", firstName='" + personnel.getFirstName() + '\'' +
                        ", lastName='" + personnel.getLastName() + '\'' +
                        ", email='" + personnel.getEmail() + '\'' +
                        ", role=" + personnel.getRole() +
                        ", departmentName='" + personnel.getDepartmentName() + '\'' +
                        ", status=" + personnel.getStatus() +
                        '}')
                .collect(Collectors.joining(", ")) : "[]") +
                ", startedAt=" + startedAt +
                ", departmentName='" + departmentName + '\'' +
                ", status=" + status +
                ", position='" + position + '\'' +
                '}';
    }
}

