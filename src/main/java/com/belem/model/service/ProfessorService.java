package com.belem.model.service;

import com.belem.model.entities.adminDomain.School;
import com.belem.model.entities.user.Professor;
import com.belem.dto.ProfessorDTO;
import com.belem.model.entities.user.User;
import com.belem.model.entities.user.enums.UserRole;
import com.belem.model.repository.ProfessorRepository;
import com.belem.model.repository.SchoolRepository;
import com.belem.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfessorDTO createProfessor(ProfessorDTO dto) {
        if (professorRepository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {
            throw new RuntimeException("Professor registration number already exists.");
        }
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        School school = schoolRepository.findById(dto.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found."));

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPasswordHash("hashed_password_placeholder");
        newUser.setRole(UserRole.ROLE_PROFESSOR);

        Professor professor = new Professor();
        professor.setFullName(dto.getFullName());
        professor.setRegistrationNumber(dto.getRegistrationNumber());
        professor.setStatus(dto.getStatus());
        professor.setSchool(school);
        professor.setUserAccount(newUser);

        Professor savedProfessor = professorRepository.save(professor);

        return convertToDto(savedProfessor);
    }

    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método 'Mapper' privado para converter Entidade -> DTO
    private ProfessorDTO convertToDto(Professor professor) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setFullName(professor.getFullName());
        dto.setRegistrationNumber(professor.getRegistrationNumber());
        dto.setStatus(professor.getStatus());
        dto.setSchoolId(professor.getSchool().getId());
        dto.setSchoolName(professor.getSchool().getName());
        dto.setUsername(professor.getUserAccount().getUsername());
        return dto;
    }
}