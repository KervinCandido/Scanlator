package com.github.kervincandido.scanlator.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class SignInForm {
	
	@NotBlank
	@Length(min = 8, max = 60)
	@Email
	private String email;
	
	@NotBlank
	@Length(min = 6, max = 20)
	private String password;

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}
}
