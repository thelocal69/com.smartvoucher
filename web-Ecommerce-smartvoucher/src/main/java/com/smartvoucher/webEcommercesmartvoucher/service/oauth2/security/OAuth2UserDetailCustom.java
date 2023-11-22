package com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class OAuth2UserDetailCustom implements OAuth2User, UserDetails{
    @Getter
    private final long id;;
    @Getter
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public OAuth2UserDetailCustom(long id, String email, String password,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static OAuth2UserDetailCustom create(UserEntity user){
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new OAuth2UserDetailCustom(
                user.getId(),
                user.getEmail(),
                user.getPwd(),
                authorities
        );
    }

    public static OAuth2UserDetailCustom create(UserEntity user, Map<String, Object> attributes){
        OAuth2UserDetailCustom oAuth2UserDetailCustom = create(user);
        oAuth2UserDetailCustom.setAttributes(attributes);
        return oAuth2UserDetailCustom;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
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

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
