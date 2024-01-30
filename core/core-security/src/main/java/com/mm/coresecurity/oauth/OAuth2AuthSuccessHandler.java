package com.mm.coresecurity.oauth;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mm.coredomain.domain.Groups;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.OAuthProvider;
import com.mm.coredomain.repository.GroupRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfraredis.repository.RedisRefreshTokenRepository;
import com.mm.coresecurity.jwt.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OAuth2AuthSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;
	private final RedisRefreshTokenRepository redisRefreshTokenRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		DefaultOAuth2User principal = (DefaultOAuth2User)authentication.getPrincipal();

		Map<String, String> properties = principal.getAttribute("kakao_account");
		String email = properties.get("email");

		Member member = getMemberElseCreateMember(email);
		List<SimpleGrantedAuthority> authorities = member.getGroups()
			.getGroupPermissions()
			.stream()
			.map(groupPermission -> new SimpleGrantedAuthority(groupPermission.getPermission().getName()))
			.toList();

		// TODO provider kakao, apple 구분
		OAuth2UserDetails userDetails = OAuth2UserDetails.builder()
			.id(member.getId())
			.provider(OAuthProvider.KAKAO)
			.authorities(authorities)
			.build();

		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken();

		redisRefreshTokenRepository.save(refreshToken, member.getId());

		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);

		log.info(">>>>>>>>>>>>>> OAUTH2 handler");

		response.addHeader("Authorization", "Bearer " + accessToken);
		// HttpResponseUtil.writeSuccessResponse(response, tokenMap);
	}

	private Member getMemberElseCreateMember(String email) {
		return memberRepository.findByEmail(email).orElseGet(() -> {
			Groups userGroup = groupRepository.findByName("USER_GROUP").orElseThrow(RuntimeException::new);
			Member member = Member.builder()
				.email(email)
				.groups(userGroup)
				.build();
			return memberRepository.save(member);
		});
	}
}
