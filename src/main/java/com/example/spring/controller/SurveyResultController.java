package com.example.spring.controller;

import com.example.spring.dto.InvestmentTypeDTO;
import com.example.spring.dto.InvestmentTypeAnswerDTO;
import com.example.spring.service.SurveyResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/survey-result")
@Slf4j
public class SurveyResultController {

    private final SurveyResultService surveyResultService;

    @Autowired
    public SurveyResultController(SurveyResultService surveyResultService) {
        this.surveyResultService = surveyResultService;
    }

    // 현재 로그인된 사용자의 점수를 조회 (userId PathVariable 제거, JWT 토큰 사용)
    @GetMapping("/total-score")
    public ResponseEntity<Integer> getTotalScore(@AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();  // JWT 토큰을 통해 userId 가져오기
        int totalScore = surveyResultService.getTotalScore();
        return ResponseEntity.ok(totalScore);
    }

    // 현재 로그인된 사용자의 투자 유형을 조회 (userId PathVariable 제거, JWT 토큰 사용)
    @GetMapping("/investment-type")
    public ResponseEntity<InvestmentTypeAnswerDTO> getInvestmentTypeByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();  // JWT 토큰을 통해 userId 가져오기
        log.info("Received userId: " + userId);

        InvestmentTypeAnswerDTO investmentTypeAnswerDTO = surveyResultService.getInvestmentTypeByUserId();
        return ResponseEntity.ok(investmentTypeAnswerDTO);
    }

    // 현재 로그인된 사용자의 투자 유형 상세 정보를 조회
    @GetMapping("/investment-type/details")
    public ResponseEntity<InvestmentTypeDTO> getInvestmentTypeDetails(@AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();  // JWT 토큰을 통해 userId 가져오기
        InvestmentTypeDTO investmentTypeDTO = surveyResultService.getInvestmentTypeDetails();
        return ResponseEntity.ok(investmentTypeDTO);
    }

}
