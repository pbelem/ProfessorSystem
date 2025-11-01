package com.belem.model.repository;

import com.belem.model.entities.adminDomain.ScheduleAvailability;
import com.belem.model.entities.user.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleAvailabilityRepository extends JpaRepository<ScheduleAvailability, Long> {
    void deleteByProfessorAndSemester(Professor professor, String semester);
}
