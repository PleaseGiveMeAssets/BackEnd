package com.example.spring.controller;

import com.example.spring.dto.InvestmentTypeDTO;
import com.example.spring.dto.InvestmentTypeAnswerDTO;
import com.example.spring.mapper.MemberMapper;
import com.example.spring.service.SurveyResultService;
import com.example.spring.service.SurveyService;
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
    private final SurveyService surveyService;

    @Autowired
    public SurveyResultController(SurveyResultService surveyResultService ,SurveyService surveyService) {
        this.surveyResultService = surveyResultService;
        this.surveyService = surveyService;
    }

    // 현재 로그인된 사용자의 점수를 조회 (memberId PathVariable 제거, JWT 토큰 사용)
    @GetMapping("/total-score")
    public ResponseEntity<Integer> getTotalScore(@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();  // JWT 토큰을 통해 memberId 가져오기
        int totalScore = surveyResultService.getTotalScore();
        return ResponseEntity.ok(totalScore);
    }

    //추가 수정!!!!
    // 설문 완료 후 투자 유형 결정 및 저장 (POST 요청 추가)
    @PostMapping("/investment-type")
    public ResponseEntity<InvestmentTypeAnswerDTO> insertOrUpdateInvestmentTypeAnswer(@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();
        log.info("Inserting or updating investment type answer for memberId: {}", memberId);

        // 투자 유형 삽입 또는 업데이트 로직 호출
        InvestmentTypeAnswerDTO investmentTypeAnswerDTO = surveyResultService.getInvestmentTypeByMemberId();
        return ResponseEntity.ok(investmentTypeAnswerDTO);
    }

    // 현재 로그인된 사용자의 투자 유형을 조회 (memberId PathVariable 제거, JWT 토큰 사용)
    @GetMapping("/investment-type")
    public ResponseEntity<InvestmentTypeAnswerDTO> getInvestmentTypeByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        InvestmentTypeAnswerDTO investmentTypeAnswerDTO = surveyResultService.getInvestmentTypeByMemberId();
        return ResponseEntity.ok(investmentTypeAnswerDTO);
    }

    // 현재 로그인된 사용자의 투자 유형 상세 정보를 조회
    @GetMapping("/investment-type/details")
    public ResponseEntity<InvestmentTypeDTO> getInvestmentTypeDetails(@AuthenticationPrincipal UserDetails userDetails) {
        InvestmentTypeDTO investmentTypeDTO = surveyResultService.getInvestmentTypeDetails();
        return ResponseEntity.ok(investmentTypeDTO);
    }

    @GetMapping("/member-nickname")
    public ResponseEntity<String> getMemberNickname(@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();
        String nickname = surveyResultService.getMemberNickname(memberId); // 서비스에서 닉네임 조회
        return ResponseEntity.ok(nickname);
    }

}
