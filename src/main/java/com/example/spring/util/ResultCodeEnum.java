package com.example.spring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그에 실패하였습니다."),

    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "회원가입하려는 아이디가 이미 존재합니다."),

    PASSWORD_VALIDATION_FAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호입니다."),

    LOGOUT_FAIL(HttpStatus.BAD_REQUEST, "로그아웃에 실패하였습니다.");

    private HttpStatus httpStatus;
    private String message;
}
