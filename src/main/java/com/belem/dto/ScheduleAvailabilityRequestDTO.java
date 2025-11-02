package com.belem.dto;


import java.util.List;
import lombok.Data;

@Data
public class ScheduleAvailabilityRequestDTO {
    private String semester;
    private List<Long> timeSlotIds; // Lista de IDs dos horários que ele está disponível
}
