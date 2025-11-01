package com.belem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Resposta enviada no login
@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String token;
}
