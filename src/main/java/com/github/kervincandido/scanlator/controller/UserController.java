package com.github.kervincandido.scanlator.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kervincandido.scanlator.controller.dto.UserDTO;
import com.github.kervincandido.scanlator.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
	private ResponseEntity<Page<UserDTO>> findUsers(
			@RequestParam(name = "email", required = false) Optional<String> email, 
			@PageableDefault Pageable pageable) {
		
		var page = email.isPresent()
			? userService.findByEmail(email.get(), pageable)
			: userService.findAll(pageable);
		
		if (page.getNumberOfElements() < 1 && email.isPresent()) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.ok(UserDTO.covertToDTO(page));
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id) {
		var user = userService.findById(id);
		
		if (user.isPresent()) {
			return ResponseEntity.ok(new UserDTO(user.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
