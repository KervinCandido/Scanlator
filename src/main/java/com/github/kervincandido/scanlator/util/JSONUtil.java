package com.github.kervincandido.scanlator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	
	public static String toJSON(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}
}
