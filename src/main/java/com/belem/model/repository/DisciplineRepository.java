package com.belem.model.repository;

import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    List<Discipline> findByStatus(EntityStatus status);
}
