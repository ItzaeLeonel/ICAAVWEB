package com.sistemasmig.icaavWeb.accounting.utils;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sistemasmig.icaavWeb.accounting.services.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        String token = extractTokenFromHeader(request);

        if (token != null && jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
        } else {
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
            response.getWriter().write("Acceso denegado");
        }
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}