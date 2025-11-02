package com.belem.model.service;

import com.belem.dto.DisciplineInterestRequestDTO;
import com.belem.dto.InterestItemDTO;
import com.belem.dto.ScheduleAvailabilityRequestDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.adminDomain.DisciplineInterest;
import com.belem.model.entities.adminDomain.ScheduleAvailability;
import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.user.Professor;
import com.belem.model.entities.user.User;
import com.belem.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AvailabilityService {

    private final ScheduleAvailabilityRepository scheduleRepository;
    private final DisciplineInterestRepository interestRepository;

    private final ProfessorRepository professorRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final DisciplineRepository disciplineRepository;
    private final UserRepository userRepository;

    public void saveScheduleAvailability(ScheduleAvailabilityRequestDTO request) {
        Professor professor = getAuthenticatedProfessor();
        String semester = request.getSemester();

        scheduleRepository.deleteByProfessorAndSemester(professor, semester);

        List<TimeSlot> timeSlots = timeSlotRepository.findAllById(request.getTimeSlotIds());
        if (timeSlots.size() != request.getTimeSlotIds().size()) {
            throw new RuntimeException("One or more TimeSlot IDs are invalid.");
        }

        timeSlots.forEach(slot -> {
            ScheduleAvailability availability = new ScheduleAvailability();
            availability.setProfessor(professor);
            availability.setTimeSlot(slot);
            availability.setSemester(semester);
            scheduleRepository.save(availability);
        });
    }

    public void saveDisciplineInterests(DisciplineInterestRequestDTO request) {
        Professor professor = getAuthenticatedProfessor();
        String semester = request.getSemester();

        interestRepository.deleteByProfessorAndSemester(professor, semester);

        for (InterestItemDTO item : request.getInterests()) {
            Discipline discipline = disciplineRepository.findById(item.getDisciplineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Discipline", "id", item.getDisciplineId()));

            if (item.getPriority() != 1 && item.getPriority() != 2) {
                throw new RuntimeException("Invalid priority value: " + item.getPriority() + ". Must be 1 or 2.");
            }

            DisciplineInterest interest = new DisciplineInterest();
            interest.setProfessor(professor);
            interest.setDiscipline(discipline);
            interest.setSemester(semester);
            interest.setPriority(item.getPriority());
            interestRepository.save(interest);
        }
    }


    @Transactional(readOnly = true)
    public List<ScheduleAvailability> getMySchedule(String semester) {
        Professor professor = getAuthenticatedProfessor();
        return scheduleRepository.findByProfessorAndSemester(professor, semester);
    }

    @Transactional(readOnly = true)
    public List<DisciplineInterest> getMyInterests(String semester) {
        Professor professor = getAuthenticatedProfessor();
        return interestRepository.findByProfessorAndSemester(professor, semester);
    }

    // ========================================================================
    // MÉTODO AUXILIAR DE SEGURANÇA
    // ========================================================================
    /**
     * Busca o 'Professor' (entidade de negócio) com base no 'User'
     * (entidade de segurança) que está atualmente logado.
     */
    private Professor getAuthenticatedProfessor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found.");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return professorRepository.findByUserAccount(user)
                .orElseThrow(() -> new RuntimeException("Authenticated user is not a Professor."));
    }
}