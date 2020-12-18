package com.github.kervincandido.scanlator.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.scanlator.model.User;
import com.github.kervincandido.scanlator.model.UserBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Test
	public void testFindByEmailAndReturnUser() throws Exception {
		User user = new UserBuilder().build();
		repository.save(user);
		
		Optional<User> optionalUser = repository.findByEmail("test@test");
		assertThat(optionalUser.isPresent(), equalTo(true));
		assertThat(optionalUser.get().getEmail(), equalTo("test@test"));
	}
	
	@Test
	public void testFindByNonExistentEmail() throws Exception {
		Optional<User> optionalUser = repository.findByEmail("test@test");
		assertThat(optionalUser.isEmpty(), equalTo(true));
	}
	
	@Test
	public void testFindByEmailWithPageableAndReturnUser() throws Exception {
		User user = new UserBuilder().build();
		repository.save(user);
		
		Page<User> optionalUser = repository.findByEmail("test@test", PageRequest.of(0, 10));
		assertThat(optionalUser.getNumberOfElements(), is(equalTo(1)));
		assertThat(optionalUser.getContent().get(0).getEmail(), is(equalTo("test@test")));
	}
	
	@Test
	public void testFindByNonExistentEmailWithPageable() throws Exception {
		Page<User> optionalUser = repository.findByEmail("test@test", PageRequest.of(0, 10));
		assertThat(optionalUser.isEmpty(), equalTo(true));
	}
}
