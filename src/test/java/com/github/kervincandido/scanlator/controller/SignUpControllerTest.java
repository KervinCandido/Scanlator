package com.github.kervincandido.scanlator.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.kervincandido.scanlator.controller.form.SignUpForm;
import com.github.kervincandido.scanlator.service.UserService;
import com.github.kervincandido.scanlator.util.JSONUtil;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SignUpControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Value("${server.port}")
    int randomServerPort;
	
	@Test
	public void testPOSTCreateNewUserAndReturnCreatedStatus() throws Exception {
		var signUpForm = new SignUpForm();
		signUpForm.setName("Test");
		signUpForm.setEmail("test@test.com");
		signUpForm.setPassword("secret");
		
		var user = signUpForm.toUser();
		user.setId(1L);
		
		when(userService.create(ArgumentMatchers.any(SignUpForm.class)))
			.thenReturn(user);
		
		
		mockMvc
			.perform(MockMvcRequestBuilders.post("/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JSONUtil.toJSON(signUpForm)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost:"+randomServerPort+"/user/1"))
			.andExpect(jsonPath("$.id", is(equalTo(1))))
			.andExpect(jsonPath("$.name", is(equalTo("Test"))))
			.andExpect(jsonPath("$.email", is(equalTo("test@test.com"))));
		
		verify(userService).create(ArgumentMatchers.any(SignUpForm.class));
	}

	@Test
	public void testPostValidFieldError() throws Exception {
		var signUpForm = new SignUpForm();
		signUpForm.setName("T");
		signUpForm.setEmail("t@t.m");
		signUpForm.setPassword("s");
		
		mockMvc
			.perform(MockMvcRequestBuilders.post("/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JSONUtil.toJSON(signUpForm)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void testPostValidatesEmailAlreadyExists() throws Exception {
		var signUpForm = new SignUpForm();
		signUpForm.setName("Test");
		signUpForm.setEmail("test@test.com");
		signUpForm.setPassword("secret");
		
		when(userService.create(ArgumentMatchers.any(SignUpForm.class)))
			.thenThrow(new IllegalArgumentException("message error"));
		
		mockMvc
			.perform(MockMvcRequestBuilders.post("/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JSONUtil.toJSON(signUpForm)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(jsonPath("$.message", is(notNullValue())))
			.andExpect(jsonPath("$.message").isString())
			.andExpect(jsonPath("$.message", is(equalTo("message error"))));
		
		verify(userService).create(ArgumentMatchers.any(SignUpForm.class));
	}
}
