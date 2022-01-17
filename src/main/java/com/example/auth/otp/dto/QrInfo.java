package com.example.auth.otp.dto;

public class QrInfo {
    private String qrUrl;
    private String secretKey;

    private QrInfo(String secretKey) {
        this.secretKey = secretKey;
    }

    public QrInfo(String qrUrl, String secretKey) {
        this.qrUrl = qrUrl;
        this.secretKey = secretKey;
    }

    public static QrInfo createQrInfoHasSerectKey(String secretKey) {
        return new QrInfo(secretKey);
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public boolean hasQrUrl() {
        return qrUrl != null;
    }
}
