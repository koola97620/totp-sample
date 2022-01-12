package com.example.auth.config.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TOTPUserDetails extends UserDetails {
    String getSecretKey();
}
