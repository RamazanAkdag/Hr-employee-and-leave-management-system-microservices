/*package com.id3;

import com.id3.model.entity.PersonnelInfo;
import com.id3.repository.IPersonnelInfoRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class PersonnelInfoServiceApplicationTest
{


    @Autowired
    IPersonnelInfoRepository personnelInfoRepository;

    @org.junit.jupiter.api.Test
    @Transactional
    public void test(){


        List<PersonnelInfo> personnelInfos = personnelInfoRepository.findAll();
        personnelInfos.forEach(System.out::println);

    }
}*/
