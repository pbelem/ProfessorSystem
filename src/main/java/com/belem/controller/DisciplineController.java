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

    /**
     * Endpoint para ADMIN: Cadastrar nova disciplina.
     */
    @PostMapping
    public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineDTO dto) {
        return new ResponseEntity<>(disciplineService.createDiscipline(dto), HttpStatus.CREATED);
    }

    /**
     * Endpoint para PROFESSOR e ADMIN: Listar disciplinas ativas (Req 2).
     */
    @GetMapping("/active")
    public ResponseEntity<List<DisciplineDTO>> getActiveDisciplines() {
        return ResponseEntity.ok(disciplineService.getAllActiveDisciplines());
    }

    /**
     * Endpoint para ADMIN: Inativar disciplina (Req 1.4.2).
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<DisciplineDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status
    ) {
        return ResponseEntity.ok(disciplineService.updateStatus(id, status));
    }
}
