package com.mm.coresecurity.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			String accessToken = resolveToken(request);
			if (accessToken != null) {
				try {
					jwtTokenProvider.validateAccessToken(accessToken);
					Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} catch (ExpiredJwtException e) {
					log.info(">>>>> access token expired {}", e);
					throw e;
				} catch (Exception e) {
					log.warn(">>>>> Authentication Failed {}", e);
					throw e;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}
}
