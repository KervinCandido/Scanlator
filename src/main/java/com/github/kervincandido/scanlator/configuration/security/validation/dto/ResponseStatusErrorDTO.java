package com.github.kervincandido.scanlator.configuration.security.validation.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResponseStatusErrorDTO {

	@NonNull
	private String message;
}
