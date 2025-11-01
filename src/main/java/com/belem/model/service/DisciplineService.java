package com.belem.model.service;

import com.belem.dto.DisciplineDTO;
import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    // (Você precisará de um 'Mapper' para converter DTO <-> Entidade)

    public DisciplineDTO createDiscipline(DisciplineDTO dto) {
        Discipline discipline = new Discipline();
        discipline.setAcronym(dto.getAcronym());
        discipline.setDescription(dto.getDescription());
        discipline.setWorkload(dto.getWorkload());
        discipline.setStatus(EntityStatus.ACTIVE);

        Discipline saved = disciplineRepository.save(discipline);
        return convertToDto(saved);
    }

    public List<DisciplineDTO> getAllActiveDisciplines() {
        return disciplineRepository.findByStatus(EntityStatus.ACTIVE).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // (Req 1.4.2)
    public DisciplineDTO updateStatus(Long id, EntityStatus status) {
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
        discipline.setStatus(status);
        Discipline updated = disciplineRepository.save(discipline);
        return convertToDto(updated);
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
}
