package com.github.kervincandido.scanlator.configuration.security.validation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.github.kervincandido.scanlator.configuration.security.validation.dto.FieldErrorDTO;
import com.github.kervincandido.scanlator.configuration.security.validation.dto.ResponseStatusErrorDTO;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FieldErrorDTO> handle(MethodArgumentNotValidException exception) {
		var bindingResult = exception.getBindingResult();
		var errors = bindingResult.getFieldErrors();
		
		return errors.stream().map( fieldError -> {
			var message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			var field = fieldError.getField();
			return new FieldErrorDTO(field, message);
		}).collect(Collectors.toList());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseStatusErrorDTO handle(ResponseStatusException exception) {
		var message = exception.getReason() != null ? exception.getReason() : exception.getMessage();
		return new ResponseStatusErrorDTO(message);
	}
}
