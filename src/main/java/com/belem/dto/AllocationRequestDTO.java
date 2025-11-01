package com.belem.dto;

import lombok.Data;

@Data
public class AllocationRequestDTO {
    private String semester;
    private Long professorId;
    private Long disciplineId;
    private Long timeSlotId;
}
