package com.example.spring.util;

public class ValidationUtils {

    // 아이디 유효성 검사: 8~15자, 영문 소문자와 숫자만 가능
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-z0-9]{8,15}$");
    }

    // 비밀번호 유효성 검사: 8~15자, 영문(대소문자 상관없음), 숫자, 특수문자(@$!%*?&) 조합 필수
    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");
    }

    // 이메일 형식
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
