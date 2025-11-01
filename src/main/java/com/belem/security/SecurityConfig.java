package com.belem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Esta é uma configuração simplificada
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // (Você precisaria criar o JwtAuthFilter e o AuthenticationProvider)
    // private final JwtAuthFilter jwtAuthFilter;
    // private final AuthenticationProvider authenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos (login)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Endpoints de ADMIN (exemplo)
                        .requestMatchers("/api/v1/schools/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/professors/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/allocations/**").hasRole("ADMIN")

                        // Endpoints de PROFESSOR (exemplo)
                        .requestMatchers(HttpMethod.GET, "/api/v1/disciplines/active").hasRole("PROFESSOR")
                        .requestMatchers("/api/v1/availability/**").hasRole("PROFESSOR")

                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // API REST não usa sessão
                );
        // .authenticationProvider(authenticationProvider) // Define o provedor
        // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT

        return http.build();
    }
}
