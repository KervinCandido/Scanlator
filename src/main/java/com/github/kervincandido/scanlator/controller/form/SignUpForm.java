package com.github.kervincandido.scanlator.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.github.kervincandido.scanlator.model.User;

import lombok.Data;

@Data
public class SignUpForm {

	@NotBlank
	@Length(min = 3, max = 60)
	private String name;
	
	@NotBlank
	@Length(min = 8, max = 60)
	@Email
	private String email;
	
	@NotBlank
	@Length(min = 6, max = 20)
	private String password;
	
	public User toUser() {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
