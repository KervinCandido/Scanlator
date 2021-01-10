package com.github.kervincandido.scanlator.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

	private String type;
	private String token;
}
