package com.github.kervincandido.scanlator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kervincandido.scanlator.controller.dto.TokenDTO;
import com.github.kervincandido.scanlator.controller.form.SignInForm;
import com.github.kervincandido.scanlator.service.TokenService;

@RestController
@RequestMapping("/sign-in")
public class SignInController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> signIn(@RequestBody @Valid SignInForm signInForm) {
		try {
			UsernamePasswordAuthenticationToken authentication = signInForm.toAuthentication();
			Authentication authenticate = authenticationManager.authenticate(authentication);
			String token = tokenService.generateToken(authenticate);
			return ResponseEntity.ok(new TokenDTO("Bearer", token));
			
		} catch (DisabledException | LockedException | BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
