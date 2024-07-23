package com.id3.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpdatePersonnelRequest {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Integer managerId;
    private String departmentName;
    private String position;
}
