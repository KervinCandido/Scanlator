package com.github.kervincandido.scanlator.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping
	private List<?> findAllUser() {
		System.out.println("LISTANDO USUARIOS");
		return Collections.emptyList();
	}
}
