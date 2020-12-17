package com.github.kervincandido.scanlator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.kervincandido.scanlator.controller.dto.UserDTO;
import com.github.kervincandido.scanlator.controller.form.SignUpForm;
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
	public UserDTO create(SignUpForm signUpForm) throws IllegalArgumentException {
		var user = signUpForm.toUser();
		
		var userDB = userRepository.findByEmail(user.getEmail());
		
		if (userDB.isPresent()) {
			throw new IllegalArgumentException("Email already registered");
		}
		
		var encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		return new UserDTO(userRepository.save(user));
	}

	public Page<UserDTO> findByEmail(String email, Pageable pageable) {
		var result = userRepository.findByEmail(email, pageable);
		return UserDTO.covertToDTO(result);
	}

	public Page<UserDTO> findAll(Pageable pageable) {
		var result = userRepository.findAll(pageable);
		return UserDTO.covertToDTO(result);
	}
	
	public Optional<UserDTO> findById(Long id) {
		var result = userRepository.findById(id);
		return result.map(UserDTO::new);
	}
}
