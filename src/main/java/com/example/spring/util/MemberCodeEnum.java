package com.example.spring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberCodeEnum {
    Y("Y"),
    NAVER("naver"),
    KAKAO("kakao"),
    START_TIME("000000"),
    END_TIME("235959"),
    SEND_NUMBER("010-4343-1517");

    private String value;
}
