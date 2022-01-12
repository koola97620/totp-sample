package com.example.auth.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum RoleAuthority implements GrantedAuthority {
    ROLE_USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
