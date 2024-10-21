package com.example.spring.controller;

import com.example.spring.dto.*;
import com.example.spring.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입
     * <p>
     * 회원가입 정보를 입력하는 메소드이다.
     *
     * @param memberDTO
     * @return
     */
    @PutMapping("/signup")
    public ResponseEntity<Integer> signup(@RequestBody MemberDTO memberDTO) {
        if (log.isInfoEnabled()) {
            log.info("signup memberDTO : {}", memberDTO);
        }
        return ResponseEntity.ok(memberService.signup(memberDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = memberService.login(loginRequest, request, response);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-id")
    public ResponseEntity<List<Map<String, Object>>> findId(@RequestBody FindIdRequestDTO findIdRequestDTO) {
        //이름+휴대폰번호로 회원 찾기
        //일치하는 회원 데이터 리턴
        return ResponseEntity.ok(memberService.findIdByNameAndPhone(findIdRequestDTO));
    }

    @PostMapping("/find-password")
    public ResponseEntity<Void> findPassword(@RequestBody FindPasswordRequestDTO findPasswordRequestDTO) {
        memberService.findPassword(findPasswordRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Integer> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        return ResponseEntity.ok(memberService.changePassword(changePasswordRequestDTO));
    }

    @GetMapping("/login/renew")
    public ResponseEntity<?> renewLogin(@CookieValue(value = "refreshToken") String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("renewLogin refreshToken : {}, request : {}, response : {}", refreshToken, request, response);
        }
        return ResponseEntity.ok(memberService.renewLogin(refreshToken, request, response));
    }
}
