package com.id3.service;

import com.id3.model.dto.CreatePersonnelRequest;
import com.id3.model.dto.DeletePersonnelRequest;
import com.id3.model.dto.UpdatePersonnelRequest;
import com.id3.model.entity.PersonnelInfo;
import com.id3.model.entity.Role;
import com.id3.model.entity.Status;
import com.id3.repository.IPersonnelInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonnelInfoManager implements IPersonnelInfoService{
    private final IPersonnelInfoRepository personnelInfoRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<PersonnelInfo> getAll() {
        return personnelInfoRepository.findAll();
    }

    @Override
    public Optional<PersonnelInfo> getById(int id) {
        return personnelInfoRepository.findById(id);
    }

    @Override
    public void createPersonnel(CreatePersonnelRequest personnelRequest) {
        PersonnelInfo manager = null;

        if (personnelRequest.getManagerMailAddr() != null) {
            manager = personnelInfoRepository.findByEmail(personnelRequest.getManagerMailAddr())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        }
        Integer managerId = (manager != null) ? manager.getPersonnelId() : null;

        String hashedPassword = passwordEncoder.encode(personnelRequest.getPassword());

        PersonnelInfo personnel = PersonnelInfo.builder()
                .firstName(personnelRequest.getFirstName())
                .lastName(personnelRequest.getLastName())
                .email(personnelRequest.getEmail())
                .passwordHash(hashedPassword) // Remember to hash the password properly
                .role(Role.valueOf(personnelRequest.getRole()))
                .departmentName(personnelRequest.getDepartmentName())
                .status(Status.ACTIVE) // Set default status as ACTIVE
                .position(personnelRequest.getPosition())
                .managerId(managerId)
                .build();

        personnelInfoRepository.save(personnel);
    }

    public void updatePersonnel(UpdatePersonnelRequest personnelRequest) {
        Optional<PersonnelInfo> optionalPersonnel = personnelInfoRepository.findById(personnelRequest.getId());
        if (!optionalPersonnel.isPresent()) {
            throw new RuntimeException("Personnel not found");
        }

        PersonnelInfo personnel = optionalPersonnel.get();

        personnel.setFirstName(personnelRequest.getFirstName() != null ? personnelRequest.getFirstName() : personnel.getFirstName());
        personnel.setLastName(personnelRequest.getLastName() != null ? personnelRequest.getLastName() : personnel.getLastName());
        personnel.setEmail(personnelRequest.getEmail() != null ? personnelRequest.getEmail() : personnel.getEmail());

        personnel.setRole(personnelRequest.getRole() != null ? Role.valueOf(personnelRequest.getRole()) : personnel.getRole());
        personnel.setDepartmentName(personnelRequest.getDepartmentName() != null ? personnelRequest.getDepartmentName() : personnel.getDepartmentName());
        personnel.setPosition(personnelRequest.getPosition() != null ? personnelRequest.getPosition() : personnel.getPosition());

        if (personnelRequest.getManagerId() != null) {
            PersonnelInfo manager = personnelInfoRepository.findById(personnelRequest.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            personnel.setManagerId(manager.getPersonnelId());
        }

        personnelInfoRepository.save(personnel);
    }

    @Override
    public void deletePersonnel(DeletePersonnelRequest deletePersonnelRequest) {
        Optional<PersonnelInfo> optionalPersonnel = personnelInfoRepository.findById(deletePersonnelRequest.getId());
        PersonnelInfo personnel = null;
        if (!optionalPersonnel.isPresent()) {
            throw new RuntimeException("Personnel not found");
        }else{
            personnel = optionalPersonnel.get();
            if(personnel.getStatus().equals(Status.ACTIVE)){
                personnel.setStatus(Status.INACTIVE);
            }
        }
        personnelInfoRepository.save(personnel);
    }
}
