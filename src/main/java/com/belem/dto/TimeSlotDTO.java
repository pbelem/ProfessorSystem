package com.belem.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.belem.model.entities.enums.ClassShift;
import com.belem.model.entities.enums.EntityStatus;
import lombok.Data;

@Data
public class TimeSlotDTO {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private ClassShift shift;
    private EntityStatus status;
}
