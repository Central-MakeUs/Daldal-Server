package com.mm.coresecurity.oauth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.minidev.json.annotate.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2UserDetails implements UserDetails {
	private Long id;
	private String email;
	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;
	private OAuthProvider provider;

	@Builder
	public OAuth2UserDetails(Long id, String email,
		Collection<? extends GrantedAuthority> authorities, OAuthProvider provider) {
		this.id = id;
		this.email = email;
		this.authorities = authorities;
		this.provider = provider;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
