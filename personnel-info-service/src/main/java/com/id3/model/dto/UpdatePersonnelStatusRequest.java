package com.id3.model.dto;

import lombok.Data;

@Data
public class UpdatePersonnelStatusRequest implements IDto {
    private String email;
    private String status;
}
