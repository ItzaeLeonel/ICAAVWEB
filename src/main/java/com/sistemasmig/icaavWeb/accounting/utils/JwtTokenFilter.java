package com.sistemasmig.icaavWeb.accounting.utils;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasmig.icaavWeb.accounting.entity.Usuario;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
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
		try {
			if ("GET".equalsIgnoreCase(request.getMethod())) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = extractTokenFromHeader(request);

			if (token != null) {
				String tokenContent = jwtService.validateToken(token);

				if (tokenContent != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					String subContent = objectMapper.readTree(tokenContent).get(Constantes.TOKEN_SUB).toString();
					TokenDetalles usuario = objectMapper.readValue(subContent, TokenDetalles.class);
					request.setAttribute(Constantes.TOKEN_USUARIO, usuario);

					filterChain.doFilter(request, response);
					return;
				}
			}

			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Acceso denegado");
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error interno del servidor");
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