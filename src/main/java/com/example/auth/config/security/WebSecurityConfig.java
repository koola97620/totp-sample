package com.example.auth.config.security;

import com.example.auth.user.application.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String JSESSIONID = "JSESSIONID";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IGoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers("/h2-console").permitAll()
                    .antMatchers("/user/add").permitAll()
                    .antMatchers(HttpMethod.GET,"/user/1").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers(HttpMethod.GET, "/login").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                    .formLogin()//.authenticationDetailsSource(new TOTPWebAuthenticationDetailsSource())
                        .loginPage("/login")//.defaultSuccessUrl("/")
                            .successHandler(new LoginSuccesaHandler())
                            .failureUrl("/login?error").failureHandler(new ExtensibleAuthenticationFailureHandler()).permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies(JSESSIONID).permitAll()
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .csrf().disable();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        ExtensibleUserDetailsAuthenticationProvider provider = new ExtensibleUserDetailsAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        provider.setGoogleAuthenticator(googleAuthenticator());
        return provider;
    }
}
