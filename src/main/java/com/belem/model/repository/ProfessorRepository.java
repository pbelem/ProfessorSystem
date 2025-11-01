package com.belem.model.repository;

import com.belem.model.entities.user.Professor;
import com.belem.model.entities.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByStatus(EntityStatus status);

    Optional<Professor> findByRegistrationNumber(String registrationNumber);
}
