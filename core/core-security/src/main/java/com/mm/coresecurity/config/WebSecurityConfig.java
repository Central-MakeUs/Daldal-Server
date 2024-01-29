package com.mm.coresecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

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
			.csrf().disable()
			.anonymous().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.rememberMe().disable()
			.logout().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.oauth2Login(oauth2Configurer ->
				oauth2Configurer
					.successHandler(oAuth2AuthSuccessHandler)
					.userInfoEndpoint()
					.userService(oAuth2UserService))

			// .securityMatchers(matchers -> matchers
			// 	.requestMatchers(
			// 		antMatcher("/login"),
			// 		antMatcher("/login/oauth2/code/kakao"),
			// 		antMatcher("/oauth2/authorization/kakao")
			// 	))

			.authorizeHttpRequests(authorize ->
				authorize
					.anyRequest().permitAll())

			.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
			.exceptionHandling(exceptionHandlingConfigurer -> {
				exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
				exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler);
			})

			.build();
	}
}


