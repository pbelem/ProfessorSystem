package com.belem.model.service;

import com.belem.dto.JwtTokenResponse;
import com.belem.exceptions.ResourceNotFoundException;
import com.belem.model.entities.user.LoginRequest;
import com.belem.model.entities.user.User;
import com.belem.model.repository.UserRepository;
import com.belem.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public JwtTokenResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", authentication.getName()));

        String token = jwtService.generateToken(user);

        return new JwtTokenResponse(token);
    }
}
