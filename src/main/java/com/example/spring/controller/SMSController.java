package com.example.spring.controller;

import com.example.spring.dto.SmsDTO;
import com.example.spring.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
@Slf4j
public class SMSController {
    private final SmsService smsService;

    @Autowired
    public SMSController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<SmsDTO> sendSms(@RequestBody SmsDTO smsDTO) {
        if (log.isInfoEnabled()) {
            log.info("sendSms smsDTO : {}", smsDTO.toString());
        }
        return ResponseEntity.ok(smsService.sendSms(smsDTO));
    }

    @PostMapping("/verification")
    public ResponseEntity<Integer> checkSmsVerification(@RequestBody SmsDTO smsDTO) {
        if (log.isInfoEnabled()) {
            log.info("checkSmsVerification smsDTO : {}", smsDTO.toString());
        }
        return ResponseEntity.ok(smsService.checkSmsVerification(smsDTO));
    }
}
