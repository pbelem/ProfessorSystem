package com.belem.model.repository;

import com.belem.model.entities.teacherContext.AcademicFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicFormationRepository extends JpaRepository<AcademicFormation, Long> {

}
