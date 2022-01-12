package com.example.auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExtensibleAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
         log.info("request : {}, response : {}, {}", request.toString(), response.toString(), exception.getClass().getTypeName());
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            response.sendRedirect("/login?error=" + exception.getMessage());
        }
    }
}
