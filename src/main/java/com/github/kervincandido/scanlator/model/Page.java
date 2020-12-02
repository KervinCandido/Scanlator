package com.github.kervincandido.scanlator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PAGE")
public class Page {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "PAGE_NUMBER", nullable = false)
	private Long pageNumber;
	
	@Column(name = "PAGE_PATH", nullable = false)
	private String url;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CHAPTER", nullable = false)
	private Chapter chapter;
	
	@Override
	public String toString() {
		return "Page [id=" + id + ", pageNumber=" + pageNumber + ", url=" + url + "]";
	}
}
