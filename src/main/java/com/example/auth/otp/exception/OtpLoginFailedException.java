package com.example.auth.otp.exception;

public class OtpLoginFailedException extends RuntimeException {
    public OtpLoginFailedException(String message) {
        super(message);
    }
}
