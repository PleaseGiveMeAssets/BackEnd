package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSms {
    String phoneFirst;
    String phoneMiddle;
    String phoneLast;
    String userId;
    String phoneVerificationCode;
    String phoneVerifiedStatus;
}
