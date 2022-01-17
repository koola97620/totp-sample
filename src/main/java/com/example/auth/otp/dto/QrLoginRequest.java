package com.example.auth.otp.dto;

public class QrLoginRequest {
    private String qrUrl;
    private String secretKey;
    private String username;
    private Integer verificationCode;

    public QrLoginRequest(String qrUrl, String secretKey, String username, Integer verificationCode) {
        this.qrUrl = qrUrl;
        this.secretKey = secretKey;
        this.username = username;
        this.verificationCode = verificationCode;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getUsername() {
        return username;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }
}
