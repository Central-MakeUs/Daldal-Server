package com.mm.coresecurity.config;

import com.mm.coresecurity.jwt.JwtAccessDeniedHandler;
import com.mm.coresecurity.jwt.JwtAuthenticationEntryPoint;
import com.mm.coresecurity.jwt.JwtAuthenticationFilter;
import com.mm.coresecurity.oauth.CustomRequestEntityConverter;
import com.mm.coresecurity.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.mm.coresecurity.oauth.OAuth2AuthSuccessHandler;
import com.mm.coresecurity.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthSuccessHandler oAuth2AuthSuccessHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        accessTokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter());

        return accessTokenResponseClient;
    }

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
                                        redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/oauth/callout/*"))
                                .tokenEndpoint(token -> token
                                        .accessTokenResponseClient(accessTokenResponseClient()))
                                .successHandler(oAuth2AuthSuccessHandler)
                                .authorizationEndpoint(config ->
                                        config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                                .userInfoEndpoint()
                                .userService(oAuth2UserService))

                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(permitAllRequests()).permitAll()
                                .anyRequest().authenticated())

                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler);
                })

                .build();
    }

    private RequestMatcher[] permitAllRequests() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/swagger-ui/**"),
                antMatcher("/swagger-ui"),
                antMatcher("/swagger-ui.html"),
                antMatcher("/swagger/**"),
                antMatcher("/swagger-resources/**"),
                antMatcher("/v3/api-docs/**"),
                antMatcher("/webjars/**"),
                antMatcher("/h2-console/**"),

                antMatcher("/oauth2/**"),
                antMatcher("/oauth/**"),
                antMatcher("/api/v1/auth/refresh-access-token")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] oauthRequests() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(GET, "/oauth2/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
}


