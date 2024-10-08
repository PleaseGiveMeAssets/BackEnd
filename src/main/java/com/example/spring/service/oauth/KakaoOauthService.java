package com.example.spring.service.oauth;

import com.example.spring.dto.LoginResponseDTO;
import com.google.gson.JsonObject;

public interface KakaoOauthService {
    String getAccessToken(String authorize_code);
    JsonObject getUserInfo(String access_Token);
    LoginResponseDTO processKakaoLogin(String code);
}
