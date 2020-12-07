package com.github.kervincandido.scanlator.controller.dto;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.github.kervincandido.scanlator.model.User;

public class UserDTOTest {

	@Test
	public void testConversionUserToUserDTO() {
		var user = new User();
		user.setId(1L);
		user.setName("Test");
		user.setEmail("test@test.com");
		
		var userDTO = new UserDTO(user);
		
		assertThat(userDTO.getId(), is(equalTo(1L)));
		assertThat(userDTO.getName(), is(equalTo("Test")));
		assertThat(userDTO.getEmail(), is(equalTo("test@test.com")));
	}

}
