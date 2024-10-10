package com.example.spring.service;


import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;

public interface SmsService {
    void sendSms(String phoneFisrt, String phoneMiddle, String phoneLast, String message);

    boolean checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO);

    boolean checkEmailVerificationCode(String emailVerificationCode);

    boolean checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO);

    boolean checkVerifyCodeForFindSignup(MemberDTO memberDTO);
}
