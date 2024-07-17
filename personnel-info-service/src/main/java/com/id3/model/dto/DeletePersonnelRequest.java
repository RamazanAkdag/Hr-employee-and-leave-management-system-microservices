package com.id3.model.dto;

import lombok.Data;

@Data
public class DeletePersonnelRequest implements IDto{
    private String email;
}
