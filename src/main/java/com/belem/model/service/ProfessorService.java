package com.belem.model.service;


import com.belem.dto.ProfessorDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.School;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.entities.user.Professor;
import com.belem.model.entities.user.User;
import com.belem.model.entities.user.enums.UserRole;
import com.belem.model.repository.ProfessorRepository;
import com.belem.model.repository.SchoolRepository;
import com.belem.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorDTO createProfessor(ProfessorDTO dto) {
        if (professorRepository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {
            throw new RuntimeException("Professor registration number already exists.");
        }
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        School school = schoolRepository.findById(dto.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", dto.getSchoolId()));

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(UserRole.ROLE_PROFESSOR);

        Professor professor = new Professor();
        professor.setFullName(dto.getFullName());
        professor.setRegistrationNumber(dto.getRegistrationNumber());
        professor.setStatus(EntityStatus.ACTIVE);
        professor.setSchool(school);
        professor.setUserAccount(newUser);

        Professor savedProfessor = professorRepository.save(professor);

        return convertToDto(savedProfessor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfessorDTO getProfessorById(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

        return convertToDto(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorDTO> getProfessorsByStatus(EntityStatus status) {
        List<Professor> entities = professorRepository.findByStatus(status);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProfessorDTO updateProfessor(Long id, ProfessorDTO dto) {
        Professor existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

        School school = schoolRepository.findById(dto.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", dto.getSchoolId()));

        existingProfessor.setFullName(dto.getFullName());
        existingProfessor.setSchool(school);

        Professor updatedProfessor = professorRepository.save(existingProfessor);

        return convertToDto(updatedProfessor);
    }

    public ProfessorDTO updateStatus(Long id, EntityStatus status) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

        professor.setStatus(status);
        Professor updatedProfessor = professorRepository.save(professor);

        return convertToDto(updatedProfessor);
    }

    public void deleteProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

        try {
            professorRepository.delete(professor);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete professor: They are currently associated with allocations or other records.");
        }
    }

    private ProfessorDTO convertToDto(Professor entity) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setRegistrationNumber(entity.getRegistrationNumber());
        dto.setStatus(entity.getStatus());
        dto.setUsername(entity.getUserAccount().getUsername());

        if (entity.getSchool() != null) {
            dto.setSchoolId(entity.getSchool().getId());
            dto.setSchoolName(entity.getSchool().getName());
        }

        dto.setPassword(null);

        return dto;
    }
    
}