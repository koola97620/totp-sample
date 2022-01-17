package com.example.auth.otp.exception;

import com.example.auth.otp.exception.OtpLoginFailedException;

public class InvalidVerificationCodeException extends OtpLoginFailedException {
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
