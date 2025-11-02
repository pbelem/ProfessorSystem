package com.belem.controller;

import com.belem.dto.DisciplineInterestRequestDTO;
import com.belem.dto.ScheduleAvailabilityRequestDTO;
import com.belem.model.entities.adminDomain.DisciplineInterest;
import com.belem.model.entities.adminDomain.ScheduleAvailability;
import com.belem.model.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/schedule")
    public ResponseEntity<String> saveScheduleAvailability(@RequestBody ScheduleAvailabilityRequestDTO request) {
        availabilityService.saveScheduleAvailability(request);
        return ResponseEntity.ok("Availability updated successfully.");
    }

    @PostMapping("/interests")
    public ResponseEntity<String> saveDisciplineInterests(@RequestBody DisciplineInterestRequestDTO request) {
        availabilityService.saveDisciplineInterests(request);
        return ResponseEntity.ok("Interests updated successfully.");
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleAvailability>> getMySchedule(@RequestParam String semester) {
        List<ScheduleAvailability> schedule = availabilityService.getMySchedule(semester);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/interests")
    public ResponseEntity<List<DisciplineInterest>> getMyInterests(@RequestParam String semester) {
        List<DisciplineInterest> interests = availabilityService.getMyInterests(semester);
        return ResponseEntity.ok(interests);
    }
}
