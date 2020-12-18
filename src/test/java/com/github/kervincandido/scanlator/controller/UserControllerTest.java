package com.github.kervincandido.scanlator.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.kervincandido.scanlator.controller.dto.UserDTO;
import com.github.kervincandido.scanlator.model.User;
import com.github.kervincandido.scanlator.model.UserBuilder;
import com.github.kervincandido.scanlator.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private List<User> user;
	
	@Before
	public void setUp() {
		this.user = List.of(
			new UserBuilder().id(1L).build(),
			new UserBuilder().id(2L).build(),
			new UserBuilder().id(3L).build(),
			new UserBuilder().id(4L).build()
		);
	}
	
	@Test
	public void testGETfindAllUser() throws Exception {
		when(userService.findAll(ArgumentMatchers.any(Pageable.class)))
			.thenReturn(UserDTO.covertToDTO(new PageImpl<>(this.user)));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(equalTo(1))));
		
		verify(userService, times(0)).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
		verify(userService).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testGETfindUserByEmail() throws Exception {
		when(userService.findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
			.thenReturn(UserDTO.covertToDTO(new PageImpl<>(List.of(this.user.get(0)))));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user?email=test@test"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(equalTo(1))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email", is(equalTo("test@test"))));
		
		verify(userService).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
		verify(userService, times(0)).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testHEADfindUserByEmail() throws Exception {
		when(userService.findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
			.thenReturn(UserDTO.covertToDTO(new PageImpl<>(List.of(this.user.get(0)))));
		
		mockMvc.perform(MockMvcRequestBuilders.head("/user?email=test@test"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(userService).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
		verify(userService, times(0)).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testGETfindUserByEmailAndReturnNotFound() throws Exception {
		when(userService.findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
			.thenReturn(UserDTO.covertToDTO(new PageImpl<>(List.of())));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user?email=test@test"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		verify(userService).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
		verify(userService, times(0)).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testHEADfindUserByEmailAndReturnNotFound() throws Exception {
		when(userService.findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
			.thenReturn(UserDTO.covertToDTO(new PageImpl<>(List.of())));
		
		mockMvc.perform(MockMvcRequestBuilders.head("/user?email=test@test"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		verify(userService).findByEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));
		verify(userService, times(0)).findAll(ArgumentMatchers.any(Pageable.class));
	}
	
	@Test
	public void testGETfindUserById() throws Exception {
		when(userService.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(new UserDTO(user.get(0))));
			
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(equalTo(1))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", is(equalTo("test"))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.email", is(equalTo("test@test"))));
			
		verify(userService).findById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void testGETfindUserByIdAndReturnNotFound() throws Exception {
		when(userService.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		verify(userService).findById(ArgumentMatchers.anyLong());
	}
}
