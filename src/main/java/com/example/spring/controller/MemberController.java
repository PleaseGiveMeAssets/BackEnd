package com.example.spring.controller;

import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class MemberController {
    @Autowired
    public MemberService memberService;

    /**
     * 회원가입
     * <p>
     * 회원가입 정보를 입력하는 메소드이다.
     *
     * @param memberDTO
     * @return
     */
    @PutMapping("signup")
    public ResponseEntity<Integer> signup(@RequestBody MemberDTO memberDTO) {
        if (log.isInfoEnabled()) {
            log.info("signup memberDTO : {}", memberDTO);
        }
        return ResponseEntity.ok(memberService.signup(memberDTO));
    }

    @PostMapping("/find-id")
    public ResponseEntity<Map<String, Object>> findId(@RequestBody FindIdRequestDTO findIdRequestDTO) {
        //이름+휴대폰번호로 회원 찾기
        //일치하는 회원 데이터 리턴
        return ResponseEntity.ok(memberService.findIdByNameAndPhone(findIdRequestDTO));
    }

    @PostMapping("/find-password")
    public ResponseEntity<Integer> findPassword(@RequestBody FindPasswordRequestDTO findPasswordRequestDTO) {
        return ResponseEntity.ok(memberService.findPassword(findPasswordRequestDTO));
    }
}
