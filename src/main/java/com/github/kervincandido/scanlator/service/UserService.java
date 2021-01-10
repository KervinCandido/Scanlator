package com.github.kervincandido.scanlator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.kervincandido.scanlator.controller.form.SignUpForm;
import com.github.kervincandido.scanlator.model.User;
import com.github.kervincandido.scanlator.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param signUpForm
	 * @return UserDTO
	 * @throws IllegalArgumentException if Email already registered
	 */
	public User create(SignUpForm signUpForm) throws IllegalArgumentException {
		var user = signUpForm.toUser();
		
		var userDB = userRepository.findByEmail(user.getEmail());
		
		if (userDB.isPresent()) {
			throw new IllegalArgumentException("Email already registered");
		}
		
		var encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}

	public Page<User> findByEmail(String email, Pageable pageable) {
		return userRepository.findByEmail(email, pageable);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
