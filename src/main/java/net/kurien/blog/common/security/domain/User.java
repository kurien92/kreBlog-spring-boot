package net.kurien.blog.common.security.domain;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 2L;

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private boolean block;
    private Collection<? extends GrantedAuthority> authorities;

    public User(Long id, String email, String password, String nickname, boolean block, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.block = block;
        this.authorities = authorities;
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean getBlock() {
        return this.block;
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
        return !block;
    }

    public void eraseCredentials() {
        this.password = null;
    }
}
