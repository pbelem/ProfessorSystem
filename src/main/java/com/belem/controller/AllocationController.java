package com.belem.controller;

import com.belem.dto.AllocationDTO;
import com.belem.dto.AllocationRequestDTO;
import com.belem.model.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationService allocationService;

    @PostMapping
    public ResponseEntity<AllocationDTO> createAllocation(@RequestBody AllocationRequestDTO request) {
        AllocationDTO newAllocation = allocationService.createAllocation(request);
        return new ResponseEntity<>(newAllocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AllocationDTO>> getAllocationsBySemester(@RequestParam String semester) {
        List<AllocationDTO> allocations = allocationService.getAllocationsBySemester(semester);
        return ResponseEntity.ok(allocations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.noContent().build();
    }

}