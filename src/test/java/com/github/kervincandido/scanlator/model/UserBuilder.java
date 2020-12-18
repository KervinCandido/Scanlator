package com.github.kervincandido.scanlator.model;

public class UserBuilder {

	private Long id;
	private String name;
	private String email;
	private String password;
	
	public UserBuilder() {
		this.name = "test";
		this.email = "test@test";
		this.password = "secret";
	}
	
	public UserBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public UserBuilder email(String email) {
		this.email = email;
		return this;
	}
	
	public UserBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public UserBuilder id(Long id) {
		this.id = id;
		return this;
	}
	
	public User build() {
		var user = new User();
		user.setId(this.id);
		user.setName(this.name);
		user.setEmail(this.email);
		user.setPassword(this.password);
		
		return user;
	}
}
