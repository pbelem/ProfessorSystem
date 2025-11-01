package com.belem.controller;

import com.belem.dto.DisciplineInterestRequestDTO;
import com.belem.dto.ScheduleAvailabilityRequestDTO;
import com.belem.model.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    /**
     * Endpoint para PROFESSOR: Salvar horários disponíveis (Req 3).
     */
    @PostMapping("/schedule")
    public ResponseEntity<String> saveScheduleAvailability(@RequestBody ScheduleAvailabilityRequestDTO request) {
        availabilityService.saveScheduleAvailability(request);
        // (Req 5: Apresentar resultado positivo)
        return ResponseEntity.ok("Availability updated successfully.");
    }

    /**
     * Endpoint para PROFESSOR: Salvar interesses em disciplinas (Req 4).
     */
    @PostMapping("/interests")
    public ResponseEntity<String> saveDisciplineInterests(@RequestBody DisciplineInterestRequestDTO request) {
        availabilityService.saveDisciplineInterests(request);
        // (Req 5: Apresentar resultado positivo)
        return ResponseEntity.ok("Interests updated successfully.");
    }
}
