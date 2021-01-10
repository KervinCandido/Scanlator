package com.github.kervincandido.scanlator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@Table(name = "PROFILE")
public class Profile implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9146984000451340151L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Override
	public String getAuthority() {
		return name;
	}

}
