package com.belem.dto;

import com.belem.model.entities.enums.EntityStatus;
import lombok.Data;

@Data
public class SchoolDTO {
    private Long id;
    private String name;
    private EntityStatus status;
}
