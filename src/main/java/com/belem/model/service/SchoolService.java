package com.belem.model.service;

import com.belem.model.entities.adminDomain.School;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public School createSchool(School school) {
        school.setStatus(EntityStatus.ACTIVE);
        return schoolRepository.save(school);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }
}
