package com.id3.repository;

import com.id3.model.entity.PersonnelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPersonnelInfoRepository extends JpaRepository<PersonnelInfo, Integer> {
    Optional<PersonnelInfo> findByEmail(String managerMailAddr);
}
