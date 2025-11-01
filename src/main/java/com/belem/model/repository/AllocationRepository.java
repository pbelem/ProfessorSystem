package com.belem.model.repository;

import com.belem.model.entities.adminDomain.Allocation;
import com.belem.model.entities.adminDomain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    //Regra de Negócio 2.1 (max 5)
    long countByDisciplineAndSemester(Discipline discipline, String semester);
}