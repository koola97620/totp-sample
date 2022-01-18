package com.example.auth.user.application;


import com.example.auth.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password);

    void delete(User user);

    User getUser(Long id);
}
