package com.example.auth.otp.ui;

import com.example.auth.otp.application.QrService;
import com.example.auth.otp.dto.QrInfo;
import com.example.auth.otp.dto.QrLoginRequest;
import com.example.auth.otp.exception.OtpLoginFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    @GetMapping("/otp-login")
    public ModelAndView otpLogin(Authentication authentication, ModelAndView modelAndView) {
        QrInfo qrInfo = qrService.getQrInfo(authentication.getName());

        modelAndView.setViewName("otp-login");
        modelAndView.addObject("qrInfo", qrInfo);
        modelAndView.addObject("username", authentication.getName());
        return modelAndView;
    }

    @PostMapping("/otp-login")
    public ModelAndView otpLoginProcess(QrLoginRequest qrLoginRequest) {
        qrService.loginByQr(qrLoginRequest);
        return new ModelAndView("redirect:/main");
    }

    @GetMapping("/main")
    public ModelAndView main(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");

        System.out.println("=== authentication.getName() : " + authentication.getName());
        return modelAndView;
    }

    @ExceptionHandler(value = OtpLoginFailedException.class)
    public ModelAndView handleExp(OtpLoginFailedException e) {
        log.error("login errorName:{}, errorMessage:{}", e.getClass().getName(), e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", e.getMessage());
        modelAndView.setViewName("redirect:/otp-login");
        return modelAndView;
    }

}
