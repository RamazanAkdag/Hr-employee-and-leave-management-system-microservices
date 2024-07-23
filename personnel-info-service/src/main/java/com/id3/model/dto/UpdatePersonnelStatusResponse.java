package com.id3.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePersonnelStatusResponse implements IDto {

    private String message;
}
