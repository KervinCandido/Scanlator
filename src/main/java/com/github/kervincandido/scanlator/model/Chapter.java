package com.github.kervincandido.scanlator.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CHAPTER")
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CHAPTER_NUMBER", nullable = false)
	private Long chapterNumber;
	
	@OneToMany(mappedBy = "chapter")
	private List<Page> pagesPath;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_LITERARY_WORK", nullable = false)
	private LiteraryWork literaryWork;
}
