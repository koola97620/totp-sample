package com.example.auth.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum RoleAuthority implements GrantedAuthority {
    ROLE_USER,TEMP_AUTHENTICATION;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
