package com.mm.coresecurity.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expiry-seconds}")
	private int expirySeconds;

	@Value("${jwt.refresh-expiry-seconds}")
	private int refreshExpirySeconds;

	public String generateAccessToken(OAuth2UserDetails userDetails) {
		Instant expirationTime = Instant.now().plusSeconds(expirySeconds);

		String authorities = null;
		if (userDetails.getAuthorities() != null) {
			authorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		}

		return Jwts.builder()
			.claim("id", userDetails.getId())
			.subject((userDetails.getUsername()))
			.issuedAt(Date.from(Instant.now()))
			.expiration(Date.from(expirationTime))
			.claim("authorities", authorities)
			.signWith(getKey())
			.compact();
	}

	public String generateRefreshToken() {
		Instant expirationTime = Instant.now().plusSeconds(refreshExpirySeconds);

		return Jwts.builder()
			.issuedAt(Date.from(Instant.now()))
			.expiration(Date.from(expirationTime))
			.signWith(getKey())
			.compact();
	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public void validateAccessToken(String token) {
		Jwts.parser().verifyWith(getKey()).build().parse(token);
	}
}
