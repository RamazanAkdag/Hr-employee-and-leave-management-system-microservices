package com.id3.service;

import com.id3.model.dto.CreatePersonnelRequest;
import com.id3.model.dto.DeletePersonnelRequest;
import com.id3.model.dto.UpdatePersonnelRequest;
import com.id3.model.dto.UpdatePersonnelStatusRequest;
import com.id3.model.entity.PersonnelInfo;

import java.util.List;
import java.util.Optional;

public interface IPersonnelInfoService {

    public List<PersonnelInfo> getAll();

    public Optional<PersonnelInfo> getById(int id);

    public void createPersonnel(CreatePersonnelRequest personnelRequest);

    public void updatePersonnel(UpdatePersonnelRequest updatePersonnelRequest);

    public void deletePersonnel(DeletePersonnelRequest deletePersonnelRequest);

    public void updatePersonnelStatus(UpdatePersonnelStatusRequest updatePersonnelStatusRequest);

}
