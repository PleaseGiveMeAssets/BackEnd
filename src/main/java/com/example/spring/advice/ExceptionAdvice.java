package com.example.spring.advice;

import com.example.spring.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    //로그인
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> invalidCredentialException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 아이디, 비번 일치x - INVALID_CREDENTIALS

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } //로그인 실패 - LOGIN_FAIL

    //로그아웃
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 로그아웃 실패 - LOGOUT_FAIL

    //회원가입
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> invalidIdException(InvalidIdException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 아이디 양식x - INVALID_ID_FORMAT

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> invalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 비밀번호 양식x - INVALID_PASSWORD_FORMAT

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> invalidEmailException(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 이메일 양식x - INVALID_EMAIL_FORMAT

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } // 회원가입 시 중복된 아이디 - DUPLICATED_MEMBER_ID

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> passwordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 비밀번호 재입력 일치x - PASSWORD_MISMATCH

    //인증코드
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<String> invalidVerificationCodeException(InvalidVerificationCodeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 인증 코드x - INVALID_VERIFICAITION_CODE

    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<String> emailVerificationException(EmailVerificationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 이메일 인증x - INVALID_EMAIL_VERIFICATION

    //내부 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
//
//    //공동 예외 처리
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleAllExceptions(Exception e) {
//        log.error("Unhandled exception: ", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
//    }


}
