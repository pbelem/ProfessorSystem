package com.belem.model.repository;

import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    List<Discipline> findByStatus(EntityStatus status);

    Optional<Object> findByAcronym(String acronym);
}
