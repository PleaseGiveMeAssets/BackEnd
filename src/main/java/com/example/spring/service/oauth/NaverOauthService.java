package com.example.spring.service.oauth;

import com.example.spring.dto.LoginResponseDTO;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NaverOauthService {
    String getNaverAccessToken(String authorize_code, String state);
    JsonObject getUserInfo(String access_Token);
    LoginResponseDTO processNaverLogin(String code, String state, HttpServletRequest request, HttpServletResponse response);
}
