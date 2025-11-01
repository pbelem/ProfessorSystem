package com.belem.controller;

import com.belem.dto.ProfessorDTO;
import com.belem.model.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO createdProfessor = professorService.createProfessor(professorDTO);
        return new ResponseEntity<>(createdProfessor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
        List<ProfessorDTO> professors = professorService.getAllProfessors();
        return ResponseEntity.ok(professors);
    }

}
