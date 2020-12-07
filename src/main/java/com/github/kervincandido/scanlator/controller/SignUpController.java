package com.github.kervincandido.scanlator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.kervincandido.scanlator.controller.dto.UserDTO;
import com.github.kervincandido.scanlator.controller.form.SignUpForm;
import com.github.kervincandido.scanlator.service.UserService;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {
	
	@Autowired
	private UserService userService;
	
	@Value("${server.port}")
    int randomServerPort;
	
	@PostMapping
	private ResponseEntity<UserDTO> createUser(@RequestBody @Valid SignUpForm signUpForm, UriComponentsBuilder uriBuild) {
		try {
			var userDTO = userService.create(signUpForm);
			var uri = uriBuild.path("/user/{id}").port(randomServerPort).buildAndExpand(userDTO.getId()).toUri();
			
			return ResponseEntity.created(uri).body(userDTO);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
