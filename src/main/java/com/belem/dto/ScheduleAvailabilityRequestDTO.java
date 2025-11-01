package com.belem.dto;


import java.util.List;
import lombok.Data;

// DTO que o Professor envia para salvar sua disponibilidade (Req 3)
@Data
public class ScheduleAvailabilityRequestDTO {
    private String semester;
    private List<Long> timeSlotIds; // Lista de IDs dos horários que ele está disponível
}
