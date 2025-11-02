package com.belem.controller;

import com.belem.dto.TimeSlotDTO;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timeslots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @PostMapping
    public ResponseEntity<TimeSlotDTO> createTimeSlot(@RequestBody TimeSlotDTO dto) {
        TimeSlotDTO createdDto = timeSlotService.createTimeSlot(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotDTO>> getAllTimeSlots() {
        List<TimeSlotDTO> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TimeSlotDTO>> getActiveTimeSlots() {
        List<TimeSlotDTO> timeSlots = timeSlotService.getAllActiveTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable Long id) {
        TimeSlotDTO timeSlot = timeSlotService.getTimeSlotById(id);
        return ResponseEntity.ok(timeSlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO dto) {
        TimeSlotDTO updatedDto = timeSlotService.updateTimeSlot(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TimeSlotDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status) {
        TimeSlotDTO updatedDto = timeSlotService.updateStatus(id, status);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }
}