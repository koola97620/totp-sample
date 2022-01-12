package com.example.auth.core.user.application;

import com.example.auth.core.user.domain.User;
import com.example.auth.core.user.domain.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final IGoogleAuthenticator googleAuthenticator;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository, IGoogleAuthenticator googleAuthenticator, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.googleAuthenticator = googleAuthenticator;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String username, String password) {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        String encodedPassword = passwordEncoder.encode(password);

        String qrUrl = createQRUrl(username, key);
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .otpQrUrl(qrUrl)
                .build();
        user.changeOtpSecretKey(key.getKey());
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private String createQRUrl(String username, GoogleAuthenticatorKey key) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("iseduOtpTest", username, key);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " is not found"));
        return user;
    }
}
