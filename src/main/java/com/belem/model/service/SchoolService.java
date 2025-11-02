package com.belem.model.service;


import com.belem.dto.SchoolDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.School;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolDTO createSchool(SchoolDTO dto) {
        // Regra de negócio: Validar se nome já existe
        if (schoolRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("School name already exists.");
        }

        School entity = convertToEntity(dto);
        entity.setStatus(EntityStatus.ACTIVE);

        School savedEntity = schoolRepository.save(entity);

        return convertToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<SchoolDTO> getAllSchools() {
        List<School> entities = schoolRepository.findAll();
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public SchoolDTO getSchoolById(Long id) {
        School entity = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", id));

        return convertToDto(entity);
    }

    public SchoolDTO updateSchool(Long id, SchoolDTO dto) {
        School existingEntity = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", id));

        existingEntity.setName(dto.getName());

        School updatedEntity = schoolRepository.save(existingEntity);

        return convertToDto(updatedEntity);
    }

    public SchoolDTO updateStatus(Long id, EntityStatus status) {
        School entity = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", id));

        entity.setStatus(status);
        School updatedEntity = schoolRepository.save(entity);

        return convertToDto(updatedEntity);
    }

    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException("School", "id", id);
        }

        try {
            schoolRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete school: It is currently associated with one or more professors.");
        }
    }


    private SchoolDTO convertToDto(School entity) {
        SchoolDTO dto = new SchoolDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private School convertToEntity(SchoolDTO dto) {
        School entity = new School();
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
