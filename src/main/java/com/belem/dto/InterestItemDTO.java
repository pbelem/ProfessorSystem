package com.belem.dto;

import lombok.Data;

@Data
public class InterestItemDTO {
    private Long disciplineId;
    private Integer priority; // 1 (baixa), 2 (alta)
}
