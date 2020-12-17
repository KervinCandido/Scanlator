package com.github.kervincandido.scanlator.configuration.security.validation.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class IllegalArgumentExceptionDTO {

	@NonNull
	private String message;
}
