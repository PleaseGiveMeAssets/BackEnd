package com.example.spring.service;

import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;

import java.util.Map;

public interface MemberService {
    // 아이디 찾기
    Map<String, Object> findIdByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    // 비밀번호 찾기
    int findPassword(FindPasswordRequestDTO findPasswordRequestDTO);
}
