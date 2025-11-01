package com.belem.model.service;


import com.belem.dto.AllocationRequestDTO;
import com.belem.model.entities.adminDomain.Allocation;
import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.user.Professor;
import com.belem.model.repository.AllocationRepository;
import com.belem.model.repository.DisciplineRepository;
import com.belem.model.repository.ProfessorRepository;
import com.belem.model.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplineRepository disciplineRepository;
    private final TimeSlotRepository timeSlotRepository;

    // (Req 2: Administrador aloca professor)
    public Allocation createAllocation(AllocationRequestDTO request) {
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        // Regra de Negócio (Req 2.1: Máximo de 5 alocações por disciplina/semestre)
        long currentAllocations = allocationRepository.countByDisciplineAndSemester(discipline, request.getSemester());
        if (currentAllocations >= 5) {
            throw new RuntimeException("Discipline " + discipline.getAcronym() + " already has 5 allocations this semester.");
        }

        // TODO: Validar se o professor tem interesse e disponibilidade (Req 2.1)
        // (Esta lógica seria mais complexa, checando DisciplineInterest e ScheduleAvailability)

        Allocation allocation = new Allocation();
        allocation.setProfessor(professor);
        allocation.setDiscipline(discipline);
        allocation.setTimeSlot(timeSlot);
        allocation.setSemester(request.getSemester());

        return allocationRepository.save(allocation);
    }
}
