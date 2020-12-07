package com.github.kervincandido.scanlator.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.scanlator.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Test
	public void testFindByEmailAndReturnUser() throws Exception {
		User user = new User();
		user.setName("Test");
		user.setEmail("test@test.com");
		user.setPassword("secret");
		repository.save(user);
		
		Optional<User> optionalUser = repository.findByEmail("test@test.com");
		assertThat(optionalUser.isPresent(), equalTo(true));
		assertThat(optionalUser.get().getEmail(), equalTo("test@test.com"));
	}
	
	@Test
	public void testFindByNonExistentEmail() throws Exception {
		Optional<User> optionalUser = repository.findByEmail("test@test.com");
		assertThat(optionalUser.isEmpty(), equalTo(true));
	}
}
