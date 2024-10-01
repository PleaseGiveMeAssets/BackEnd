package com.example.spring.service;

import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSms(String phoneFisrt, String phoneMiddle, String phoneLast, String message) {
        // TODO: 실제 SMS 발송 로직 구현 (예: 외부 API 사용)
    }

    @Override
    public boolean checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO) {
        return true;
    }

    @Override
    public boolean checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO) {
        return true;
    }


}
