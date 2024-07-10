package com.id3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class PersonnelInfoServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(PersonnelInfoServiceApplication.class,args);
    }
}
 /*
      Optional<PersonnelInfo> optionalPersonnel = personnelInfoRepository.findById(personnelRequest.getId());
        if (!optionalPersonnel.isPresent()) {
            throw new RuntimeException("Personnel not found");
        }

        PersonnelInfo personnel = optionalPersonnel.get();

        if (personnelRequest.getFirstName() != null) {
            personnel.setFirstName(personnelRequest.getFirstName());
        }

        if (personnelRequest.getLastName() != null) {
            personnel.setLastName(personnelRequest.getLastName());
        }

        if (personnelRequest.getEmail() != null) {
            personnel.setEmail(personnelRequest.getEmail());
        }

        if (personnelRequest.getRole() != null) {
            personnel.setRole(Role.valueOf(personnelRequest.getRole()));
        }

        if (personnelRequest.getManagerId() != null) {
            PersonnelInfo manager = personnelInfoRepository.findById(personnelRequest.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            personnel.setManager(manager);
        }

        if (personnelRequest.getDepartmentName() != null) {
            personnel.setDepartmentName(personnelRequest.getDepartmentName());
        }

        if (personnelRequest.getPosition() != null) {
            personnel.setPosition(personnelRequest.getPosition());
        }

        personnelInfoRepository.save(personnel);*/