package com.belem.dto;

import com.belem.model.entities.enums.EntityStatus;
import lombok.Data;

// DTO (Data Transfer Object) para transferir dados de e para o frontend
// Evita expor a Entidade JPA diretamente na API
@Data
public class ProfessorDTO {
    private Long id;
    private String registrationNumber;
    private String fullName;
    private EntityStatus status;
    private Long schoolId;
    private String schoolName;
    private String username;
    private String password;
}
