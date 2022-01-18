package com.example.auth.otp.application;

import com.example.auth.otp.dto.QrInfo;
import com.example.auth.otp.dto.QrLoginRequest;
import com.example.auth.otp.exception.InvalidVerificationCodeException;
import com.example.auth.otp.exception.OtpLoginFailedException;
import com.example.auth.user.domain.User;
import com.example.auth.user.domain.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class QrServiceImpl implements QrService {

    private final IGoogleAuthenticator googleAuthenticator;
    private final UserRepository userRepository;

    public QrServiceImpl(IGoogleAuthenticator googleAuthenticator, UserRepository userRepository) {
        this.googleAuthenticator = googleAuthenticator;
        this.userRepository = userRepository;
    }

    public QrInfo getQrInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        if (user.hasSecretKey()) {
            return QrInfo.createQrInfoHasSerectKey(user.getSecretKey());
        }

        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        //String qrUrl = createQRUrl(username, key);
        StringBuilder sb = new StringBuilder();
        String issuer = "iseduOtpTest";
        String qrUrl =
                sb.append("otpauth://totp/")
                        .append(issuer)
                        .append(":")
                        .append(username)
                        .append("?secret=")
                        .append(key.getKey())
                        .append("&issuer=")
                        .append(issuer)
                        .toString();
        return new QrInfo(qrUrl, key.getKey());
    }

    @Override
    @Transactional
    public void loginByQr(QrLoginRequest qrLoginRequest) {
        validate(qrLoginRequest);

        User user = userRepository.findByUsername(qrLoginRequest.getUsername()).orElseThrow(EntityNotFoundException::new);
        if (!user.hasSecretKey()) {
            user.changeOtpSecretKey(qrLoginRequest.getSecretKey());
        }

        verifyVerificationCode(qrLoginRequest, user);
    }

    private void verifyVerificationCode(QrLoginRequest qrLoginRequest, User user) {
        if (qrLoginRequest.getVerificationCode() != null) {
            if (!googleAuthenticator.authorize(user.getSecretKey(), qrLoginRequest.getVerificationCode())) {
                throw new OtpLoginFailedException("유효한 verification code 가 아닙니다.");
            }
        } else {
            throw new OtpLoginFailedException("verification Code 를 입력해주세요");
        }
    }

    private void validate(QrLoginRequest qrLoginRequest) {
        if (!overVerificationCodeLenghSix(qrLoginRequest.getVerificationCode())) {
            throw new InvalidVerificationCodeException("Verification Code 는 6자리의 숫자이어야 합니다.");
        }
    }

    private boolean overVerificationCodeLenghSix(Integer verificationCode) {
        return verificationCode / 100000 >= 1 || verificationCode / 100000 <= 9;
    }

    private String createQRUrl(String username, GoogleAuthenticatorKey key) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("iseduOtpTest", username, key);
    }

}
