# Google TOTP Sameple

## 실행

- api 클라이언트로 http://localhost:8080/user/add 호출
```
{
    "username": "abc@gmail.com",
    "password": "1234"
}
``` 
- http://localhost:8080/user/add 의 response 에 있는 qr 링크를 브라우저에 입력
- google otp 앱에서 qr 링크 인식
- http://localhost:8080 에서 로그인
- SUCCESS

## 참고
- https://github.com/ihoneymon/spring-security-2step-verification
- https://java.ihoney.pe.kr/449
- https://creampuffy.tistory.com/89