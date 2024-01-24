package com.mm.coresecurity.jwt;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.mm.coredomain.domain.OAuthProvider;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

		Collection<? extends GrantedAuthority> authorities = null;
		if (claims.get("") != null) {
			authorities = Arrays.stream(claims.get("authorities").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();
		}

		// TODO provider 애플 추가
		OAuth2UserDetails principal = new OAuth2UserDetails(claims.get("id", Long.class), claims.getSubject(),
			authorities,
			OAuthProvider.KAKAO);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public void validateAccessToken(String token) {
		Jwts.parser().verifyWith(getKey()).build().parse(token);
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(accessToken)
				.getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
}
