package com.example.auth.config.security;


import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 로그인폼에 있는 "totp-verification-code" 정보를 읽어온다.
 *
 */

@Getter
public class TOTPWebAuthenticationDetails extends WebAuthenticationDetails {
    public static final String TOTP_VERIFICATION_CODE = "totp-verification-code";
    private Integer totpkey;

    public TOTPWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String totpKeyString = request.getParameter(TOTP_VERIFICATION_CODE);
        if (StringUtils.hasText(totpKeyString)) {
            try {
                this.totpkey = Integer.valueOf(totpKeyString);
            } catch (NumberFormatException e) {
                this.totpkey = null;
            }
        }
    }
}
