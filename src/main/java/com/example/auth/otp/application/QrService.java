package com.example.auth.otp.application;

import com.example.auth.otp.dto.QrInfo;
import com.example.auth.otp.dto.QrLoginRequest;

public interface QrService {
    QrInfo getQrInfo(String username);

    void loginByQr(QrLoginRequest qrLoginRequest);
}
