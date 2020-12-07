package com.github.kervincandido.scanlator.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.scanlator.controller.dto.UserDTO;
import com.github.kervincandido.scanlator.controller.form.SignUpForm;
import com.github.kervincandido.scanlator.model.User;
import com.github.kervincandido.scanlator.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;

	private SignUpForm signUpForm;
	
	@Before
	public void initSignUpForm() {
		signUpForm = new SignUpForm();
		signUpForm.setName("test");
		signUpForm.setEmail("test@test.com");
		signUpForm.setPassword("secret");
	}
	
	@Test
	public void testCreateAndReturnUserDTO() throws Exception {
		var user = signUpForm.toUser();
		user.setId(1L);
		
		when(userRepository.findByEmail(ArgumentMatchers.anyString()))
			.thenReturn(Optional.empty());
		
		when(userRepository.save(ArgumentMatchers.any(User.class)))
				.thenReturn(user);
		
		UserDTO userDTO = userService.create(signUpForm);
		
		assertThat(userDTO, is(notNullValue()));
		assertThat(userDTO.getId(), is(equalTo(1L)));
		assertThat(userDTO.getName(), is(equalTo("test")));
		assertThat(userDTO.getEmail(), is(equalTo("test@test.com")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmailAlreadyExists() throws Exception {
		var user = signUpForm.toUser();
		user.setId(1L);
		
		when(userRepository.findByEmail(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(user));
		
		userService.create(signUpForm);
	}
}
