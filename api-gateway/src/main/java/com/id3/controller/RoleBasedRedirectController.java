package com.id3.controller;

import com.id3.config.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/ui")
@RequiredArgsConstructor
public class RoleBasedRedirectController {

    private final JwtService jwtService;

    @GetMapping
    public String redirectBasedOnRole(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        List<String> roles = Collections.singletonList(jwtService.extractRole(token));

        log.info("Roles: " + roles);

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/ui/admin";
        } else if (roles.contains("ROLE_HR")) {
            return "redirect:/ui/hr";
        } else if (roles.contains("ROLE_EMPLOYEE")) {
            return "redirect:/ui/employee";
        } else {
            return "redirect:/ui/auth";
        }
    }
}