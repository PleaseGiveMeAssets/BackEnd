package com.example.spring.controller.oauth;

import com.example.spring.dto.LoginResponseDTO;
import com.example.spring.service.oauth.KaKaoOauthServiceImpl;
import com.example.spring.service.oauth.NaverOauthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SocialLoginController {
    private final KaKaoOauthServiceImpl kaKaoOauthService;
    private final NaverOauthServiceImpl naverOauthService;

    @GetMapping("/login/code/kakao")
    public ResponseEntity<LoginResponseDTO> oauthKakao(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO loginResponse = kaKaoOauthService.processKakaoLogin(code, response, request);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/login/code/naver")
    public ResponseEntity<LoginResponseDTO> oauthNaver(@RequestParam("code") String code,
                                                       @RequestParam("state") String state,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        LoginResponseDTO loginResponse = naverOauthService.processNaverLogin(code, state, request, response);
        return ResponseEntity.ok(loginResponse);
    }
}