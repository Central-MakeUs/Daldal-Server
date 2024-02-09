package com.mm.coresecurity.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		Map<String, Object> attributes;
		if (registrationId.contains("apple")) {
			String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
			attributes = decodeJwtTokenPayload(idToken);
			attributes.put("id_token", idToken);
		} else {
			OAuth2User oAuth2User = super.loadUser(userRequest);
			attributes = oAuth2User.getAttributes();
		}

		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		return new DefaultOAuth2User(Collections.emptyList(), attributes, userNameAttributeName);
	}

	public Map<String, Object> decodeJwtTokenPayload(String jwtToken) {
		Map<String, Object> jwtClaims = new HashMap<>();
		try {
			String[] parts = jwtToken.split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
			String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();

			Map<String, Object> map = mapper.readValue(decodedString, Map.class);
			jwtClaims.putAll(map);

		} catch (JsonProcessingException e) {
			log.error("decodeJwtToken: {}-{} / jwtToken : {}", e.getMessage(), e.getCause(), jwtToken);
		}
		return jwtClaims;
	}
}
