package com.example.spring.advice;

import com.example.spring.exception.*;
import com.example.spring.util.ResultCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } //로그인 실패 - LOGIN_FAIL

    @ExceptionHandler(SocialOauthException.class)
    public ResponseEntity<String> socialOauthException(SocialOauthException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    } //소셜로그인 실패

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

    @ExceptionHandler(NoMatchingUserException.class)
    public ResponseEntity<String> noMatchingUserException(NoMatchingUserException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } // 일치하는 회원X

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

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

    @ExceptionHandler(UserAnswerProcessingException.class)
    public ResponseEntity<String> handleUserAnswerProcessingException(UserAnswerProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //옵션 선택값 입력/수정 실패
    } //설문지 옵션값 insert update 오류

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // DB 리소스를 찾을 수 없음 (404)
    } // 리소스를 찾을 수 없음 - DB에서 데이터 가져올 때 오류

    @ExceptionHandler(TotalScoreCalculationException.class)
    public ResponseEntity<String> TotalScoreCalculationException(TotalScoreCalculationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    } // 총 점수 계산 실패

    @ExceptionHandler(InvestmentTypeAnswerProcessingException.class)
    public ResponseEntity<String> InvestmentTypeAnswerProcessingException(InvestmentTypeAnswerProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    } // 투자 유형 답변 처리 실패

    @ExceptionHandler(InvestmentTypeRetrievalException.class)
    public ResponseEntity<String> InvestmentTypeRetrievalException(InvestmentTypeRetrievalException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    } // 투자 유형 조회 실패


    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<String> emailVerificationException(EmailVerificationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } // 이메일 인증x - INVALID_EMAIL_VERIFICATION

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(ResultCodeEnum.SESSION_EXPIRATION.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(ResultCodeEnum.NO_EXIST_DATA.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    ResponseEntity<String> userIdNotFoundException(UserIdNotFoundException e) {
        return ResponseEntity.status(ResultCodeEnum.NO_EXIST_USER_ID.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(SessionExpiredException.class)
    ResponseEntity<String> sessionExpiredException(SessionExpiredException e) {
        return ResponseEntity.status(ResultCodeEnum.SESSION_EXPIRATION.getHttpStatus()).body(e.getMessage());
    }

    //내부 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
