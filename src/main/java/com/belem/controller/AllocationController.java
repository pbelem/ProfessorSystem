package com.belem.controller;

import com.belem.dto.AllocationRequestDTO;
import com.belem.model.entities.adminDomain.Allocation;
import com.belem.model.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationService allocationService;

    /**
     * Endpoint para ADMIN: Alocar professor em disciplina (Req 2).
     */
    @PostMapping
    public ResponseEntity<Allocation> createAllocation(@RequestBody AllocationRequestDTO request) {
        // (Nota: Idealmente, retornaria um AllocationDTO)
        Allocation newAllocation = allocationService.createAllocation(request);
        return new ResponseEntity<>(newAllocation, HttpStatus.CREATED);
    }

    /**
     * Endpoint para ADMIN: Obter listas/relatórios (Req 3, 3.1, 3.2, 3.3).
     */
    @GetMapping("/reports/by-shift")
    public ResponseEntity<String> getReportByShift() {
        // (Req 3.2: ...ordenada por turno)
        // ... lógica de serviço para gerar o relatório
        return ResponseEntity.ok("Report placeholder (Req 3.2)");
    }
}
