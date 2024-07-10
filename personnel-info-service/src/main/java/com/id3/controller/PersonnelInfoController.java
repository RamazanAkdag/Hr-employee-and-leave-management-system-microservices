package com.id3.controller;

import com.id3.model.dto.*;
import com.id3.model.entity.PersonnelInfo;
import com.id3.service.IPersonnelInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personnel-info")
@RequiredArgsConstructor
public class PersonnelInfoController {
    private final IPersonnelInfoService personnelInfoService;


    @GetMapping
    public ResponseEntity<List<PersonnelInfo>> getAll(){
        return ResponseEntity.ok(personnelInfoService.getAll());
    }

    @GetMapping("/{personnel-id}")
    public ResponseEntity<PersonnelInfo> getById(@PathVariable(name = "personnel-id") int id){
        return ResponseEntity.ok(personnelInfoService.getById(id).get());
    }

    @PostMapping
    public ResponseEntity<CreatePersonnelResponse> createPersonnel(@RequestBody CreatePersonnelRequest createPersonnelDto){
        personnelInfoService.createPersonnel(createPersonnelDto);
        var response = CreatePersonnelResponse.builder().message("Personel created successfully").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdatePersonnelResponse> updatePersonnel(@RequestBody UpdatePersonnelRequest updatePersonnelDto){
        personnelInfoService.updatePersonnel(updatePersonnelDto);
        var response = UpdatePersonnelResponse.builder().message("Personnel Updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<DeletePersonnelResponse> deletePersonnel(@RequestBody DeletePersonnelRequest deletePersonnelDto){
        personnelInfoService.deletePersonnel(deletePersonnelDto);
        var res = DeletePersonnelResponse.builder().message("Personnel deactivated successfully").build();
        return ResponseEntity.ok(res);
    }


}