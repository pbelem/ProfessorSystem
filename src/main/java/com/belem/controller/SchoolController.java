package com.belem.controller;

import com.belem.dto.SchoolDTO;
import com.belem.model.entities.enums.EntityStatus;
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

    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolDTO dto) {
        SchoolDTO createdSchool = schoolService.createSchool(dto);
        return new ResponseEntity<>(createdSchool, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        List<SchoolDTO> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable Long id) {
        SchoolDTO school = schoolService.getSchoolById(id);
        return ResponseEntity.ok(school);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable Long id, @RequestBody SchoolDTO dto) {
        SchoolDTO updatedSchool = schoolService.updateSchool(id, dto);
        return ResponseEntity.ok(updatedSchool);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SchoolDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status) {
        SchoolDTO updatedSchool = schoolService.updateStatus(id, status);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.noContent().build();
    }
}
