package com.mm.api.domain.auth.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfraredis.repository.RedisRefreshTokenRepository;
import com.mm.coresecurity.jwt.JwtTokenProvider;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final RedisRefreshTokenRepository redisRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	public TokenResponse refreshAccessToken(RefreshTokenRequest request) {
		Long memberId = redisRefreshTokenRepository.findByRefreshToken(request.refreshToken())
			.orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED));

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		OAuth2UserDetails oauth2UserDetails = createOauth2UserDetails(member);

		String accessToken = jwtTokenProvider.generateAccessToken(oauth2UserDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken();

		redisRefreshTokenRepository.save(refreshToken, memberId);

		return new TokenResponse(accessToken, refreshToken);
	}

	private OAuth2UserDetails createOauth2UserDetails(Member member) {
		List<SimpleGrantedAuthority> authorities = member.getGroups()
			.getGroupPermissions()
			.stream()
			.map(groupPermission -> new SimpleGrantedAuthority(groupPermission.getPermission().getName()))
			.toList();

		return OAuth2UserDetails.builder()
			.id(member.getId())
			.provider(member.getProvider())
			.authorities(authorities)
			.build();
	}
}
