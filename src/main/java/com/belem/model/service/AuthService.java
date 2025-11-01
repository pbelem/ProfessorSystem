package com.belem.model.service;

import com.belem.dto.JwtTokenResponse;
import com.belem.model.entities.user.LoginRequest;
import com.belem.model.repository.UserRepository;
import com.belem.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public JwtTokenResponse login(LoginRequest request) {
        // Autentica o usuário (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Se autenticado, busca o usuário e gera o token
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String token = jwtService.generateToken(user);
        return new JwtTokenResponse(token);
    }
}
