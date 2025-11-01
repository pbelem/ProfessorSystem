package com.belem.model.repository;

import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByStatus(EntityStatus status);
}