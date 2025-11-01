package com.belem.model.repository;

import com.belem.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método essencial para o Spring Security
    Optional<User> findByUsername(String username);
}