package com.maistruk.springangular.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.maistruk.springangular.model.Role;
import com.maistruk.springangular.model.Status;
import com.maistruk.springangular.model.User;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails{
    
    private final String username;
    private final String password;
    private Role role;
    private final boolean isActive;
    
    public SecurityUser(String username, String password, Role role, boolean isActive) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(getRole());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), 
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                Set.of(user.getRole())
                );
    }

}
