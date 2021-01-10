package com.github.kervincandido.scanlator.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.kervincandido.scanlator.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	private String secret;
	private Long expirateTime;
	
	public TokenService() {
		this.secret = "secret";
		this.expirateTime = 86400000L;
	}
	
	public String generateToken(Authentication authenticate) {
		User user = (User) authenticate.getPrincipal();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirateDate = now.plus(this.expirateTime, ChronoField.MILLI_OF_DAY.getBaseUnit());
		
		return Jwts.builder()
			.setIssuer("Scanlator-api")
			.setSubject(user.getId().toString())
			.setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
			.setExpiration(Date.from(expirateDate.atZone(ZoneId.systemDefault()).toInstant()))
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}
	
	public String extractToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null) {
			return null;
		}
		
		String[] tokenInfo = token.split(" ");
		
		if (tokenInfo.length < 2) {
			return null;
		}
		
		if (tokenInfo[0].equals("Bearer")) {
			return tokenInfo[1];			
		}
		
		return null;
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		String id = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		return Long.parseLong(id);
	}
}
