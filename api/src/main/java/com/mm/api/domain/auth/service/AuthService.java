package com.mm.api.domain.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.auth.dto.request.LoginKakaoRequest;
import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.domain.member.service.MemberService;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Groups;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.OAuthProvider;
import com.mm.coredomain.repository.GroupRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfrafeign.oauth.client.KakaoAuthInfoClient;
import com.mm.coreinfrafeign.oauth.client.KakaoAuthLoginClient;
import com.mm.coreinfrafeign.oauth.dto.response.KakaoAuthInfoResponse;
import com.mm.coreinfrafeign.oauth.dto.response.KakaoAuthLoginResponse;
import com.mm.coreinfraredis.repository.RedisRefreshTokenRepository;
import com.mm.coresecurity.jwt.JwtTokenProvider;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	@Value("${kakao.client-id}")
	private String KAKAO_CLIENT_ID;
	@Value("${kakao.client-secret}")
	private String KAKAO_CLIENT_SECRET;

	private final KakaoAuthLoginClient kakaoAuthLoginClient;
	private final KakaoAuthInfoClient kakaoAuthInfoClient;

	private final RedisRefreshTokenRepository redisRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;
	private final MemberService memberService;

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

	public TokenResponse loginKakao(LoginKakaoRequest request) {
		KakaoAuthLoginResponse kakaoLoginAccessToken = getKakaoLoginAccessToken(request);
		KakaoAuthInfoResponse kakaoAuthInfoResponse = getKakaoAuthInfoResponse(kakaoLoginAccessToken);
		return kakaoLoginSuccessHandler(kakaoAuthInfoResponse.kakaoAccount().getEmail());
	}

	public void logout(RefreshTokenRequest request) {
		redisRefreshTokenRepository.delete(request.refreshToken());
	}

	public MemberInfoResponse getMe(OAuth2UserDetails userDetails) {
		return memberService.getMemberInfo(userDetails.getId());
	}

	public void getWithdrawl(OAuth2UserDetails userDetails) {
		Member member = getMember(userDetails.getId());
		memberRepository.delete(member);
	}

	public TokenResponse getSuperToken() {
		Member member = memberRepository.findById(1L)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		OAuth2UserDetails oauth2UserDetails = createOauth2UserDetails(member);

		String accessToken = jwtTokenProvider.generateSuperToken(oauth2UserDetails);

		return new TokenResponse(accessToken, null);
	}

	public OAuth2UserDetails createOauth2UserDetails(Member member) {
		List<SimpleGrantedAuthority> authorities = member.getGroups()
			.getGroupPermissions()
			.stream()
			.map(groupPermission -> new SimpleGrantedAuthority(groupPermission.getPermission().getName()))
			.toList();

		return OAuth2UserDetails.builder()
			.id(member.getId())
			.provider(OAuthProvider.KAKAO) // 일단 카카오로
			.authorities(authorities)
			.build();
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}

	private KakaoAuthInfoResponse getKakaoAuthInfoResponse(KakaoAuthLoginResponse kakaoLoginAccessToken) {
		return kakaoAuthInfoClient.getInfo("Bearer " + kakaoLoginAccessToken.accessToken());
	}

	private KakaoAuthLoginResponse getKakaoLoginAccessToken(LoginKakaoRequest request) {
		Map<String, Object> parmaMap = new HashMap<>();
		parmaMap.put("grant_type", "authorization_code");
		parmaMap.put("client_id", KAKAO_CLIENT_ID);
		parmaMap.put("client_secret", KAKAO_CLIENT_SECRET);
		parmaMap.put("redirect_uri", request.redirectUri());
		parmaMap.put("code", request.code());

		return kakaoAuthLoginClient.getAccessToken(parmaMap);
	}

	private TokenResponse kakaoLoginSuccessHandler(String email) {
		Member member = getMemberElseCreateMember(email);
		List<SimpleGrantedAuthority> authorities = member.getGroups()
			.getGroupPermissions()
			.stream()
			.map(groupPermission -> new SimpleGrantedAuthority(groupPermission.getPermission().getName()))
			.toList();

		OAuth2UserDetails userDetails = OAuth2UserDetails.builder()
			.id(member.getId())
			.provider(OAuthProvider.KAKAO)
			.authorities(authorities)
			.build();

		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken();

		redisRefreshTokenRepository.save(refreshToken, member.getId());

		return new TokenResponse(accessToken, refreshToken);
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
