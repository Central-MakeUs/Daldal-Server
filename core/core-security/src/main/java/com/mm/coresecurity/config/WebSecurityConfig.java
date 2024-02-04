package com.mm.coresecurity.config;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import com.mm.coresecurity.jwt.JwtAccessDeniedHandler;
import com.mm.coresecurity.jwt.JwtAuthenticationEntryPoint;
import com.mm.coresecurity.jwt.JwtAuthenticationFilter;
import com.mm.coresecurity.oauth.OAuth2AuthSuccessHandler;
import com.mm.coresecurity.service.OAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final OAuth2UserService oAuth2UserService;
	private final OAuth2AuthSuccessHandler oAuth2AuthSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.cors().configurationSource(request -> {
				CorsConfiguration cors = new CorsConfiguration();
				cors.setAllowedOriginPatterns(List.of("*"));
				cors.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
				cors.setAllowedHeaders(List.of("*"));
				cors.setAllowCredentials(true);
				return cors;
			}).and()
			.csrf().disable()
			.anonymous().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.rememberMe().disable()
			.logout().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.oauth2Login(oauth2Configurer ->
				oauth2Configurer
					.redirectionEndpoint(
						redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/oauth2/callback/*"))
					.successHandler(oAuth2AuthSuccessHandler)
					.userInfoEndpoint()
					.userService(oAuth2UserService))

			.authorizeHttpRequests(authorize ->
				authorize
					// .requestMatchers(oauthRequests()).authenticated()
					.anyRequest().permitAll())

			.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
			.exceptionHandling(exceptionHandlingConfigurer -> {
				exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
				exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler);
			})

			.build();
	}

	private RequestMatcher[] oauthRequests() {
		List<RequestMatcher> requestMatchers = List.of(
			antMatcher(GET, "/oauth2/**")
		);
		return requestMatchers.toArray(RequestMatcher[]::new);
	}
}


