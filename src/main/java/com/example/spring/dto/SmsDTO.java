package com.example.spring.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsDTO {
    String phoneFirst;
    String phoneMiddle;
    String phoneLast;
    String phoneVerificationCode;
}
