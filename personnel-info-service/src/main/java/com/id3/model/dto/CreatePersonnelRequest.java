package com.id3.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePersonnelRequest implements IDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String managerMailAddr;
    private String departmentName;
    private String position;

}
