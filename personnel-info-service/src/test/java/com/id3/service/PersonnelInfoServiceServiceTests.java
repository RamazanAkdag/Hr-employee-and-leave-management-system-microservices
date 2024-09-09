/*package com.id3.service;

import com.id3.model.dto.CreatePersonnelRequest;
import com.id3.model.entity.PersonnelInfo;
import com.id3.repository.IPersonnelInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonnelInfoServiceServiceTests {
    @Mock
    private IPersonnelInfoRepository personnelInfoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonnelInfoManager personnelInfoService;

    @Test
    void createPersonnel_whenManagerMailIsNotEmpty_shouldSavePersonnelWithManager() {
        // Arrange
        CreatePersonnelRequest request = CreatePersonnelRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("EMPLOYEE")
                .departmentName("Engineering")
                .position("Developer")
                .managerMailAddr("manager@example.com")
                .build();

        PersonnelInfo manager = PersonnelInfo.builder()
                .personnelId(1)
                .email("manager@example.com")
                .build();

        // Ensure that the repository returns the manager when searched by email
        when(personnelInfoRepository.findByEmail(anyString())).thenReturn(Optional.of(manager));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(personnelInfoRepository.save(any(PersonnelInfo.class))).thenReturn(new PersonnelInfo());

        // Act
        personnelInfoService.createPersonnel(request);

        // Assert
        verify(personnelInfoRepository, times(1)).findByEmail(anyString());
        verify(personnelInfoRepository, times(1)).save(any(PersonnelInfo.class));
    }

    @Test
    void createPersonnel_whenManagerMailIsEmpty_shouldSavePersonnelWithManager() {
        // Arrange
        CreatePersonnelRequest request = CreatePersonnelRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("EMPLOYEE")
                .departmentName("Engineering")
                .position("Developer")
                .build();



        // Ensure that the repository returns the manager when searched by email
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(personnelInfoRepository.save(any(PersonnelInfo.class))).thenReturn(new PersonnelInfo());

        // Act
        personnelInfoService.createPersonnel(request);

        // Assert
        verify(personnelInfoRepository, times(1)).save(any(PersonnelInfo.class));
    }






}
*/