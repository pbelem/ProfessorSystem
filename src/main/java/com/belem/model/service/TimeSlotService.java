package com.belem.model.service;

import com.belem.dto.TimeSlotDTO;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.adminDomain.TimeSlot;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotDTO createTimeSlot(TimeSlotDTO dto) {

        if (timeSlotRepository.findByDayOfWeekAndStartTimeAndEndTime(
                dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime()).isPresent()) {
            throw new RuntimeException("This exact time slot already exists.");
        }

        TimeSlot entity = convertToEntity(dto);
        entity.setStatus(EntityStatus.ACTIVE);

        TimeSlot savedEntity = timeSlotRepository.save(entity);

        return convertToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotDTO> getAllTimeSlots() {
        List<TimeSlot> entities = timeSlotRepository.findAll();
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TimeSlotDTO> getAllActiveTimeSlots() {
        List<TimeSlot> entities = timeSlotRepository.findByStatus(EntityStatus.ACTIVE);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TimeSlotDTO getTimeSlotById(Long id) {
        TimeSlot entity = timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));

        return convertToDto(entity);
    }

    public TimeSlotDTO updateTimeSlot(Long id, TimeSlotDTO dto) {
        TimeSlot existingEntity = timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));

        existingEntity.setDayOfWeek(dto.getDayOfWeek());
        existingEntity.setStartTime(dto.getStartTime());
        existingEntity.setEndTime(dto.getEndTime());
        existingEntity.setShift(dto.getShift());

        TimeSlot updatedEntity = timeSlotRepository.save(existingEntity);

        return convertToDto(updatedEntity);
    }

    public TimeSlotDTO updateStatus(Long id, EntityStatus status) {
        TimeSlot entity = timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));

        entity.setStatus(status);
        TimeSlot updatedEntity = timeSlotRepository.save(entity);

        return convertToDto(updatedEntity);
    }

    public void deleteTimeSlot(Long id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new ResourceNotFoundException("TimeSlot", "id", id);
        }

        try {
            timeSlotRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete time slot: It is currently in use by one or more professors or allocations.");
        }
    }

    private TimeSlotDTO convertToDto(TimeSlot entity) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setId(entity.getId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setShift(entity.getShift());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private TimeSlot convertToEntity(TimeSlotDTO dto) {
        TimeSlot entity = new TimeSlot();
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setShift(dto.getShift());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
