package com.belem.controller;

import com.belem.dto.ProfessorDTO;
import com.belem.model.entities.enums.EntityStatus;
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

    @GetMapping("/by-status")
    public ResponseEntity<List<ProfessorDTO>> getProfessorsByStatus(@RequestParam EntityStatus status) {
        List<ProfessorDTO> professors = professorService.getProfessorsByStatus(status);
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long id) {
        ProfessorDTO professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorDTO dto) {
        ProfessorDTO updatedProfessor = professorService.updateProfessor(id, dto);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ProfessorDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status) {
        ProfessorDTO updatedProfessor = professorService.updateStatus(id, status);
        return ResponseEntity.ok(updatedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
