package com.belem.controller;

import com.belem.model.entities.adminDomain.School;
import com.belem.model.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    /**
     * Endpoint para ADMIN: Cadastrar nova escola.
     */
    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        // (Nota: Idealmente, usaríamos DTOs aqui também)
        School createdSchool = schoolService.createSchool(school);
        return new ResponseEntity<>(createdSchool, HttpStatus.CREATED);
    }

    /**
     * Endpoint para ADMIN e PROFESSOR: Listar escolas.
     */
    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }

    // TODO: Implementar PUT para atualizar status (Req 1.1.1)
}
