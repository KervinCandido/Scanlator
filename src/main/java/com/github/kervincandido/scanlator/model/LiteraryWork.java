package com.github.kervincandido.scanlator.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LITERARY_WORK")
public class LiteraryWork {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@ManyToMany
	@JoinTable(name = "AUTHOR_LITERARY_WORK",
		joinColumns = @JoinColumn(name = "ID_AUTHOR", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "ID_LITERARY_WORK", referencedColumnName = "ID")
	)
	private List<Author> authors;
	
	@ManyToMany
	@JoinTable(name = "ARTIST_LITERARY_WORK",
		joinColumns = @JoinColumn(name = "ID_ARTIST", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "ID_LITERARY_WORK", referencedColumnName = "ID")
	)
	private List<Artist> artists;
	
	@ManyToMany
	@JoinTable(name = "GENRE_LITERARY_WORK",
		joinColumns = @JoinColumn(name = "ID_GENRE", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "ID_LITERARY_WORK", referencedColumnName = "ID")
	)
	private List<Genre> genres;
	
	@OneToMany(mappedBy = "literaryWork")
	private List<Chapter> chapter;
}
