package com.example.auth.config.security;

import com.warrenstrange.googleauth.IGoogleAuthenticator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Slf4j
public class ExtensibleUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Setter
    private PasswordEncoder passwordEncoder;
    @Setter
    private UserDetailsService userDetailsService;
    @Setter
    private IGoogleAuthenticator googleAuthenticator;


    @Override
    public void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("additionalAuthenticationChecks: username={}", userDetails.getUsername());
        if (authentication.getCredentials() == null) {
            log.error("Authentication failed: no credential provided");
            throw new BadCredentialsException("No credentials");
        }

        String credentialsPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(credentialsPassword, userDetails.getPassword())) {
            log.error("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("BadCredentials");
        }
    }

    @Override
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("retrieveUser : {}", username);
        TOTPUserDetails retrieveUser;

        try {
            retrieveUser = (TOTPUserDetails) userDetailsService.loadUserByUsername(username);

            if (retrieveUser == null) {
                throw new InternalAuthenticationServiceException("UserDetails is null");
            }

            if (!StringUtils.hasText(retrieveUser.getSecretKey())) {
                throw new BadCredentialsException("User don't registry TOTP");
            }

            Integer verificationCode = ((TOTPWebAuthenticationDetails) authentication.getDetails()).getTotpkey();
            if (verificationCode != null) {
                if (!googleAuthenticator.authorize(retrieveUser.getSecretKey(), verificationCode)) {
                    throw new BadCredentialsException("Invalid VerificationCode");
                }
            } else {
                throw new BadCredentialsException("TOTP is mandatory");
            }
        } catch (UsernameNotFoundException notFoundException) {
            throw notFoundException;
        } catch (Exception authenticationProblem) {
            throw new InternalAuthenticationServiceException(authenticationProblem.getMessage(), authenticationProblem);
        }
        return retrieveUser;
    }

}
