package com.hias.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetail extends User {

    private String name;
    private List<String> roles;

    private Long primaryKey;

    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Long primaryKey) {
        super(username, password, authorities);
        this.roles = authorities
                .stream()
                .map(k -> k.getAuthority()).collect(Collectors.toList());
        this.primaryKey = primaryKey;
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
