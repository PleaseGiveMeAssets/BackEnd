package com.example.spring.service;

import com.example.spring.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface MemberService {
    // 회원가입
    int signup(MemberDTO memberDTO);

    // 소셜 회원가입
    int socialSignup(SocialMemberDTO socialMemberDTO);

    // 아이디 찾기
    Map<String, Object> findIdByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    // 비밀번호 찾기
    int findPassword(FindPasswordRequestDTO findPasswordRequestDTO);

    // 로그인
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response);

    // 소셜로그인
    LoginResponseDTO socialLogin(LoginRequestDTO loginRequestDTO, HttpServletResponse response);

    // 로그아웃
    void logout(HttpServletRequest request, HttpServletResponse response);

    LoginResponseDTO renewLogin(String userId, String token, HttpServletResponse response);
}
