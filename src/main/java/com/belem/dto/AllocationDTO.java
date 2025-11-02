package com.belem.dto;

import lombok.Data;

@Data
public class AllocationDTO {
    private Long id;
    private String semester;

    private Long professorId;
    private String professorName;

    private Long disciplineId;
    private String disciplineAcronym;

    private Long timeSlotId;
    private String timeSlotDescription;
}