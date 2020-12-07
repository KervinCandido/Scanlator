package com.github.kervincandido.scanlator.configuration.security.validation.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class FieldErrorDTO {

	@NonNull
	private String fieldName;
	
	@NonNull
	private String message;
}
