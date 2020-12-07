package com.github.kervincandido.scanlator.controller.form;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class SignUpFormTest {

	@Test
	public void testConversionSignUpFormToUser() {
		var signUpForm = new SignUpForm();
		signUpForm.setName("Test");
		signUpForm.setEmail("test@test.com");
		signUpForm.setPassword("secret");
		
		var user = signUpForm.toUser();
		
		assertThat(user.getId(), is(nullValue()));
		assertThat(user.getName(), is(equalTo("Test")));
		assertThat(user.getEmail(), is(equalTo("test@test.com")));
		assertThat(user.getPassword(), is(equalTo("secret")));
	}

}
