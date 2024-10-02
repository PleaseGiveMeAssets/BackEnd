package com.example.spring.service;


import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;

public interface SmsService {
    void sendSms(String phoneFisrt, String phoneMiddle, String phoneLast, String message);

    boolean checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO);

    boolean checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO);
}
