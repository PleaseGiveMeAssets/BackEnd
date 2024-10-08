package com.example.spring.controller.oauth;

import com.example.spring.dto.LoginResponseDTO;
import com.example.spring.service.oauth.KaKaoOauthServiceImpl;
import com.example.spring.service.oauth.NaverOauthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SocialLoginController {
    private final KaKaoOauthServiceImpl kaKaoOauthService;
    private final NaverOauthServiceImpl naverOauthService;

    @GetMapping("/login/code/kakao")
    public ResponseEntity<LoginResponseDTO> oauthKakao(@RequestParam("code") String code) {
        LoginResponseDTO loginResponse = kaKaoOauthService.processKakaoLogin(code);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/login/code/naver")
    public ResponseEntity<LoginResponseDTO> oauthNaver(@RequestParam("code") String code,
                                                       @RequestParam("state") String state) {
        LoginResponseDTO loginResponse = naverOauthService.processNaverLogin(code, state);
        return ResponseEntity.ok(loginResponse);
    }
}