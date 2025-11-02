package com.belem.controller;

import com.belem.dto.DisciplineDTO;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @PostMapping
    public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineDTO dto) {
        DisciplineDTO createdDto = disciplineService.createDiscipline(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines() {
        List<DisciplineDTO> disciplines = disciplineService.getAllDisciplines();
        return ResponseEntity.ok(disciplines);
    }

    @GetMapping("/active")
    public ResponseEntity<List<DisciplineDTO>> getActiveDisciplines() {
        List<DisciplineDTO> disciplines = disciplineService.getAllActiveDisciplines();
        return ResponseEntity.ok(disciplines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDisciplineById(@PathVariable Long id) {
        DisciplineDTO discipline = disciplineService.getDisciplineById(id);
        return ResponseEntity.ok(discipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline(@PathVariable Long id, @RequestBody DisciplineDTO dto) {
        DisciplineDTO updatedDto = disciplineService.updateDiscipline(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DisciplineDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status) {
        DisciplineDTO updatedDto = disciplineService.updateStatus(id, status);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }
}