package com.belem.model.service;

import com.belem.dto.AllocationDTO;
import com.belem.dto.AllocationRequestDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.Allocation;
import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.user.Professor;
import com.belem.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplineRepository disciplineRepository;
    private final TimeSlotRepository timeSlotRepository;

    private final ScheduleAvailabilityRepository scheduleRepository;
    private final DisciplineInterestRepository interestRepository;

    public AllocationDTO createAllocation(AllocationRequestDTO request) {
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", request.getProfessorId()));
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResourceNotFoundException("Discipline", "id", request.getDisciplineId()));
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", request.getTimeSlotId()));

        String semester = request.getSemester();

        long currentAllocations = allocationRepository.countByDisciplineAndSemester(discipline, semester);
        if (currentAllocations >= 5) {
            throw new RuntimeException("Discipline '" + discipline.getAcronym() + "' already has 5 allocations this semester.");
        }

        if (!interestRepository.existsByProfessorAndDisciplineAndSemester(professor, discipline, semester)) {
            throw new RuntimeException("Professor '" + professor.getFullName() + "' did not show interest in this discipline.");
        }

        if (!scheduleRepository.existsByProfessorAndTimeSlotAndSemester(professor, timeSlot, semester)) {
            throw new RuntimeException("Professor '" + professor.getFullName() + "' is not available at this time slot.");
        }

        Allocation allocation = new Allocation();
        allocation.setProfessor(professor);
        allocation.setDiscipline(discipline);
        allocation.setTimeSlot(timeSlot);
        allocation.setSemester(semester);

        Allocation savedEntity = allocationRepository.save(allocation);

        return convertToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<AllocationDTO> getAllocationsBySemester(String semester) {
        List<Allocation> entities = allocationRepository.findBySemester(semester);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteAllocation(Long id) {
        if (!allocationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Allocation", "id", id);
        }
        allocationRepository.deleteById(id);
    }


    private AllocationDTO convertToDto(Allocation entity) {
        AllocationDTO dto = new AllocationDTO();
        dto.setId(entity.getId());
        dto.setSemester(entity.getSemester());

        dto.setProfessorId(entity.getProfessor().getId());
        dto.setProfessorName(entity.getProfessor().getFullName());

        dto.setDisciplineId(entity.getDiscipline().getId());
        dto.setDisciplineAcronym(entity.getDiscipline().getAcronym());

        dto.setTimeSlotId(entity.getTimeSlot().getId());
        dto.setTimeSlotDescription(String.format("%s %s-%s",
                entity.getTimeSlot().getDayOfWeek(),
                entity.getTimeSlot().getStartTime(),
                entity.getTimeSlot().getEndTime()
        ));

        return dto;
    }
}
