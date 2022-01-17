package com.example.auth.user.application;

import com.example.auth.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void test() {
        String username = "jdragon0319@gmail.com";
        String password = "1234";
        User user = userService.register(username, password);

        assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();
    }

}