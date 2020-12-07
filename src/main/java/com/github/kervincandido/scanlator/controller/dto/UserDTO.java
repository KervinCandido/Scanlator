package com.github.kervincandido.scanlator.controller.dto;

import com.github.kervincandido.scanlator.model.User;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String name;
	private String email;

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
	}
}
