package com.example.spring.service.oauth;

import com.example.spring.dto.LoginResponseDTO;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface KakaoOauthService {
    String getAccessToken(String authorize_code);
    JsonObject getUserInfo(String access_Token);
    LoginResponseDTO processKakaoLogin(String code, HttpServletResponse response, HttpServletRequest request);
    String kakaoLogout();
}
