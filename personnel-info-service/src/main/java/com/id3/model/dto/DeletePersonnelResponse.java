package com.id3.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeletePersonnelResponse implements IDto {
    private String message;
}
