package com.belem.model.service;

import com.belem.dto.DisciplineDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Injeta o 'final'
@Transactional
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineDTO createDiscipline(DisciplineDTO dto) {
        if (disciplineRepository.findByAcronym(dto.getAcronym()).isPresent()) {
            throw new RuntimeException("Discipline acronym already exists.");
        }

        Discipline entity = new Discipline();
        entity.setAcronym(dto.getAcronym());
        entity.setDescription(dto.getDescription());
        entity.setWorkload(dto.getWorkload());
        entity.setStatus(EntityStatus.ACTIVE);

        Discipline savedEntity = disciplineRepository.save(entity);

        return convertToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<DisciplineDTO> getAllDisciplines() {
        List<Discipline> entities = disciplineRepository.findAll();

        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DisciplineDTO getDisciplineById(Long id) {
        Discipline entity = disciplineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discipline", "id", id));

        return convertToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<DisciplineDTO> getAllActiveDisciplines() {
        List<Discipline> entities = disciplineRepository.findByStatus(EntityStatus.ACTIVE);

        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DisciplineDTO updateDiscipline(Long id, DisciplineDTO dto) {
        Discipline existingEntity = disciplineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discipline", "id", id));

        existingEntity.setAcronym(dto.getAcronym());
        existingEntity.setDescription(dto.getDescription());
        existingEntity.setWorkload(dto.getWorkload());

        Discipline updatedEntity = disciplineRepository.save(existingEntity);

        return convertToDto(updatedEntity);
    }

    public DisciplineDTO updateStatus(Long id, EntityStatus status) {
        Discipline entity = disciplineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discipline", "id", id));

        entity.setStatus(status);
        Discipline updatedEntity = disciplineRepository.save(entity);

        return convertToDto(updatedEntity);
    }

    public void deleteDiscipline(Long id) {
        if (!disciplineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Discipline", "id", id);
        }

        disciplineRepository.deleteById(id);
    }


    private DisciplineDTO convertToDto(Discipline entity) {
        DisciplineDTO dto = new DisciplineDTO();
        dto.setId(entity.getId());
        dto.setAcronym(entity.getAcronym());
        dto.setDescription(entity.getDescription());
        dto.setWorkload(entity.getWorkload());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private Discipline convertToEntity(DisciplineDTO dto) {
        Discipline entity = new Discipline();
        // Não setamos o ID, pois é para criação
        entity.setAcronym(dto.getAcronym());
        entity.setDescription(dto.getDescription());
        entity.setWorkload(dto.getWorkload());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}