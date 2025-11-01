package com.belem.dto;

import com.belem.model.entities.enums.EntityStatus;
import lombok.Data;

@Data
public class DisciplineDTO {
    private Long id;
    private String acronym;
    private String description;
    private Integer workload;
    private EntityStatus status;
}
