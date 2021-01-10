package com.github.kervincandido.scanlator.configuration.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.kervincandido.scanlator.model.User;
import com.github.kervincandido.scanlator.service.TokenService;
import com.github.kervincandido.scanlator.service.UserService;

public class TokenAuthenticatorFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UserService userService;
	
	public TokenAuthenticatorFilter(TokenService tokenService, UserService userService) {
		this.tokenService = tokenService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		String token = tokenService.extractToken(request);
		
		if (isValidToken(token)) {
			authenticate(token);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private boolean isValidToken(String token) {
		return token != null && tokenService.isValidToken(token);
	}

	private void authenticate(String token) {
		Long id = tokenService.getUserId(token);
		
		Optional<User> userOptional = userService.findById(id);
		
		userOptional.ifPresent(user -> {
			SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		});
	}
}
