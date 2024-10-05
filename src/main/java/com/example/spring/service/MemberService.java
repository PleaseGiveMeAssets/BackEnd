package com.example.spring.service;

import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.LoginRequestDTO;
import com.example.spring.dto.MemberDTO;

import java.util.Map;

public interface MemberService {
    // 회원가입
    int signup(MemberDTO memberDTO);

    // 아이디 찾기
    Map<String, Object> findIdByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    // 비밀번호 찾기
    int findPassword(FindPasswordRequestDTO findPasswordRequestDTO);

    // 로그인
    String login(LoginRequestDTO loginRequest);

    // 로그아웃
    void logout(String token);
}
