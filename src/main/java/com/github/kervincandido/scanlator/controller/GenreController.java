package com.github.kervincandido.scanlator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kervincandido.scanlator.repository.GenreRepository;


@RestController
@RequestMapping("/genre")
public class GenreController {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@GetMapping
	public String teste() {
		genreRepository.findAll();
		return "OK!";
	}
}
