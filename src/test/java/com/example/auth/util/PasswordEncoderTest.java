package com.example.auth.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    void encodeTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String encode = passwordEncoder.encode(rawPassword);

        assertThat(passwordEncoder.matches(rawPassword, "$2a$10$f0XjWfTD5cH3BsSrfA8SDu8pS/sIxD4TY6S0XCQdTxTjPU1OY.awG"));
        System.out.println(encode);
    }
}
