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
    KAKAO_LOGIN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "카카오와 통신 과정에서 오류가 발생했습니다."),
    NAVER_LOGIN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "네이버와 통신 과정에서 오류가 발생했습니다."),
    ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT, "일반 회원으로 가입된 계정입니다. 해당 방식으로 로그인해 주세요."),
    KAKAO_ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT, "카카오로 가입된 계정입니다. 해당 방식으로 로그인해 주세요."),
    NAVER_ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT, "네이버로 가입된 계정입니다. 해당 방식으로 로그인해 주세요."),
    //로그아웃
    LOGOUT_FAIL(HttpStatus.BAD_REQUEST, "로그아웃에 실패하였습니다."),
    //아이디,비밀번호찾기
    NO_MATCHING_USER(HttpStatus.NOT_FOUND, "입력한 정보와 일치하는 회원이 없습니다."),
    //회원가입
    INVALID_ID_FORMAT(HttpStatus.BAD_REQUEST, "8~15자 영문 소문자/숫자"),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "8~15자 영문/숫자/특수문자 조합"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 아닙니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "같은 아이디가 존재합니다."),
    NO_EXIST_USER_ID(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다."),
    //인증번호x
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "인증번호가 올바르지 않습니다."),
    INVALID_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다."),

    NO_EXIST_TERMS_OF_USE(HttpStatus.BAD_REQUEST, "이용약관이 존재하지 않습니다."),

    SESSION_EXPIRATION(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다."),

    NO_EXIST_DATA(HttpStatus.BAD_REQUEST, "데이터가 존재하지 않습니다.");

    private HttpStatus httpStatus;
    private String message;
}
