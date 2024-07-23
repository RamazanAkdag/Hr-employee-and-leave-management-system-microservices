package com.id3.model.dto;

import com.id3.model.entity.PersonnelInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements IDto{
    private String token;
    private int userId;

    private String role;
}