package com.belem.model.service;

import com.belem.dto.DisciplineInterestRequestDTO;
import com.belem.dto.ScheduleAvailabilityRequestDTO;
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
public class AvailabilityService {

    private final ScheduleAvailabilityRepository scheduleRepository;
    private final DisciplineInterestRepository interestRepository;
    private final ProfessorRepository professorRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final DisciplineRepository disciplineRepository;
    private final UserRepository userRepository;

    // (Req 3: Professor informa horários disponíveis)
    @Transactional
    public void saveScheduleAvailability(ScheduleAvailabilityRequestDTO request) {
        Professor professor = getAuthenticatedProfessor();
        String semester = request.getSemester();

        // 1. Limpa a disponibilidade antiga para este semestre
        scheduleRepository.deleteByProfessorAndSemester(professor, semester);

        // 2. Busca os TimeSlots válidos
        List<TimeSlot> timeSlots = timeSlotRepository.findAllById(request.getTimeSlotIds());

        // 3. Salva os novos
        timeSlots.forEach(slot -> {
            ScheduleAvailability availability = new ScheduleAvailability();
            availability.setProfessor(professor);
            availability.setTimeSlot(slot);
            availability.setSemester(semester);
            scheduleRepository.save(availability);
        });
    }

    // (Req 4: Professor informa disciplinas de interesse)
    @Transactional
    public void saveDisciplineInterests(DisciplineInterestRequestDTO request) {
        Professor professor = getAuthenticatedProfessor();
        String semester = request.getSemester();

        // (Lógica similar: limpar interesses antigos do semestre)
        // ...

        // Salva os novos interesses
        request.getInterests().forEach(item -> {
            Discipline discipline = disciplineRepository.findById(item.getDisciplineId())
                    .orElseThrow(() -> new RuntimeException("Discipline not found"));

            DisciplineInterest interest = new DisciplineInterest();
            interest.setProfessor(professor);
            interest.setDiscipline(discipline);
            interest.setSemester(semester);
            interest.setPriority(item.getPriority());
            interestRepository.save(interest);
        });
    }

    // Método auxiliar para buscar o Professor logado
    private Professor getAuthenticatedProfessor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // (Assume que User.professorProfile está mapeado)
        return professorRepository.findById(user.getProfessorProfile().getId())
                .orElseThrow(() -> new RuntimeException("Professor profile not found for user"));
    }
}
