package com.belem.model.repository;

import com.belem.model.entities.adminDomain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<Object> findByName(String name);
}
