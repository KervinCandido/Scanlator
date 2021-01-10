package com.github.kervincandido.scanlator.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
		
		User userDTO = userService.create(signUpForm);
		
		assertThat(userDTO, is(notNullValue()));
		assertThat(userDTO.getId(), is(equalTo(1L)));
		assertThat(userDTO.getName(), is(equalTo("test")));
		assertThat(userDTO.getEmail(), is(equalTo("test@test.com")));
		
		verify(userRepository).findByEmail(ArgumentMatchers.anyString());
		verify(userRepository).save(ArgumentMatchers.any(User.class));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateUserWithEmailAlreadyExistsAndReturnException() throws Exception {
		var user = signUpForm.toUser();
		user.setId(1L);
		
		when(userRepository.findByEmail(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(user));
		
		userService.create(signUpForm);
		
		verify(userRepository).findByEmail(ArgumentMatchers.anyString());
	}
	
	@Test
	public void testFindByEmail() {
		when(userRepository.findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
			.thenReturn(new PageImpl<>(List.of(signUpForm.toUser())));
		
		var pageEmail = userRepository.findByEmail("test", PageRequest.of(0, 10));
		assertThat(pageEmail.getNumberOfElements(), is(equalTo(1)));
		
		verify(userRepository).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testFindAll() {
		when(userRepository.findAll(ArgumentMatchers.any(Pageable.class)))
			.thenReturn(new PageImpl<>(List.of(signUpForm.toUser())));
		
		var pageEmail = userRepository.findAll(PageRequest.of(0, 10));
		assertThat(pageEmail.getNumberOfElements(), is(equalTo(1)));
		
		verify(userRepository).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testFindById() {
		when(userRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(signUpForm.toUser()));
		
		var optionalUser = userRepository.findById(1L);
		
		assertThat(optionalUser, is(notNullValue()));
		assertThat(optionalUser.isPresent(), is(true));
		
		verify(userRepository).findById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void testFindByIdAndReturnOptionalEmpty() {
		when(userRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.empty());
		
		Optional<User> optionalUser = userRepository.findById(1L);
		
		assertThat(optionalUser, is(notNullValue()));
		assertThat(optionalUser.isEmpty(), is(true));
		
		verify(userRepository).findById(ArgumentMatchers.anyLong());
	}
}
