package com.example.spring.service;


import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.dto.SmsDTO;

public interface SmsService {
    SmsDTO sendSms(SmsDTO smsDTO);

    int checkSmsVerification(SmsDTO smsDTO);

    boolean checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO);

    boolean checkEmailVerificationCode(String emailVerificationCode);

    boolean checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO);

    boolean checkVerifyCodeForFindSignup(MemberDTO memberDTO);
}
