package com.example.spring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    //로그인
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    //로그아웃
    LOGOUT_FAIL(HttpStatus.BAD_REQUEST, "로그아웃에 실패하였습니다."),
    //회원가입
    INVALID_ID_FORMAT(HttpStatus.BAD_REQUEST, "8~15자 영문 소문자/숫자"),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "8~15자 영문/숫자/특수문자 조합"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 아닙니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "같은 아이디가 존재합니다."),
    //인증번호x
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "인증번호가 올바르지 않습니다."),
    INVALID_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다."),

    NO_EXIST_TERMS_OF_USE(HttpStatus.BAD_REQUEST, "필수 이용약관이 존재하지 않습니다.");

    private HttpStatus httpStatus;
    private String message;
}
