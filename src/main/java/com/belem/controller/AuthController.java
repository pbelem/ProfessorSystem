package com.belem.controller;

import com.belem.model.entities.user.LoginRequest;
import com.belem.model.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        String token = authService.login(loginRequest);
//        return ResponseEntity.ok(new JwtTokenResponse(token));

        return ResponseEntity.ok("Login endpoint reached. Token should be generated here.");
    }
}
