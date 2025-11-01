package com.belem.dto;

import java.util.List;
import lombok.Data;

@Data
public class DisciplineInterestRequestDTO {
    private String semester;
    private List<InterestItemDTO> interests;
}