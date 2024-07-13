package com.id3.config;

import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/**", "/eureka/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/leave-request").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/leave-request").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/leave-request/accept").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/leave-request/reject").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/leave-request/{user_id}").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/leave-request/cancel").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/personnel-info").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/personnel-info/{personnel_id}").hasAnyRole("HR", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/personnel-info").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/personnel-info/update").hasAnyRole("HR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/personnel-info").hasAnyRole("HR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
