package com.belem.model.repository;

import com.belem.model.entities.adminDomain.DisciplineInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineInterestRepository extends JpaRepository<DisciplineInterest, Long> {
}
