package com.github.kervincandido.scanlator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kervincandido.scanlator.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
