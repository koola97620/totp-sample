# Google TOTP Sameple

## 실행

- 어플리케이션 실행
- http://localhost:8080 
    - username: abc@gmail.com 또는 jdragon
    - password: 1234 입력
- QR코드 google otp 앱에서 인식
- 코드 입력하면 로그인 완료.
-  http://localhost:8080/logout 후 재로그인 하면 qr코드가 표시되지 않는다.


## 참고
- https://github.com/ihoneymon/spring-security-2step-verification
- https://java.ihoney.pe.kr/449
- https://creampuffy.tistory.com/89
- https://medium.com/geekculture/few-ways-to-generate-qr-code-using-javascript-54b6b5220c4f
- https://developer-rooney.tistory.com/182
- https://github.com/google/google-authenticator/wiki/Key-Uri-Format