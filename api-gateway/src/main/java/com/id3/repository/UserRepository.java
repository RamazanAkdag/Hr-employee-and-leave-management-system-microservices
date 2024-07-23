package com.id3.repository;

import com.id3.model.entity.PersonnelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PersonnelInfo,Integer> {
    public PersonnelInfo findByEmail(String email);
}
