package com.id3.model.hrPage;

import lombok.Data;

@Data
public class CreatePersonnelRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String managerMailAddr;
    private String departmentName;
    private String position;
}
