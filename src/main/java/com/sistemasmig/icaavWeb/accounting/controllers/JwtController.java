package com.sistemasmig.icaavWeb.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemasmig.icaavWeb.accounting.services.JwtService;



@RestController
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @GetMapping("/generateToken")
    public String generateToken(@RequestParam String username) {
        String token = jwtService.generateToken(username);
        return token;
    }
    
    @GetMapping("/api/secure-endpoint")
    public String testEndpoint() {
        return "¡Este es un endpoint seguro! Puedes acceder porque proporcionaste un token JWT válido.";
    }
}