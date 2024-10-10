package com.example.spring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberCodeEnum {
    Y("Y"),
    START_TIME("000000"),
    END_TIME("235959");

    private String value;
}
