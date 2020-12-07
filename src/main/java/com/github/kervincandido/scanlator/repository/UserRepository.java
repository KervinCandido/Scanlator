package com.github.kervincandido.scanlator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kervincandido.scanlator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
