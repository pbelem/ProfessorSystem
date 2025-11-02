package com.belem.model.repository;

import com.belem.model.entities.adminDomain.ScheduleAvailability;
import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.user.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAvailabilityRepository extends JpaRepository<ScheduleAvailability, Long> {
    void deleteByProfessorAndSemester(Professor professor, String semester);

    List<ScheduleAvailability> findByProfessorAndSemester(Professor professor, String semester);

    boolean existsByProfessorAndTimeSlotAndSemester(Professor professor, TimeSlot timeSlot, String semester);
}
