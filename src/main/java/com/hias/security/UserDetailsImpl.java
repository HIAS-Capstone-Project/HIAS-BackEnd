package com.hias.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private String email;

	private String image;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String id, String username, String password,
						   Collection<? extends GrantedAuthority> authorities, String email, String image) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.email = email;
		this.image = image;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = Collections
				.singletonList(new SimpleGrantedAuthority(user.getRole()));
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				authorities,
				user.getEmail(),
				user.getImage());
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
