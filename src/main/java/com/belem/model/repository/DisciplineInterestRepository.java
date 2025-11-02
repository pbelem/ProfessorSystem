package com.belem.model.repository;

import com.belem.model.entities.adminDomain.Discipline;
import com.belem.model.entities.adminDomain.DisciplineInterest;
import com.belem.model.entities.user.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineInterestRepository extends JpaRepository<DisciplineInterest, Long> {
    void deleteByProfessorAndSemester(Professor professor, String semester);

    List<DisciplineInterest> findByProfessorAndSemester(Professor professor, String semester);

    boolean existsByProfessorAndDisciplineAndSemester(Professor professor, Discipline discipline, String semester);
}
