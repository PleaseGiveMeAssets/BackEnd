package com.example.spring.controller;

import com.example.spring.dto.MemberAnswerDTO;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.service.SurveyResultService;
import com.example.spring.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/survey")
@Slf4j
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyResultService surveyResultService;

    @Autowired
    public SurveyController(SurveyService surveyService, SurveyResultService surveyResultService) {
        this.surveyService = surveyService; this.surveyResultService = surveyResultService;
    }


    // JWT 토큰에서 memberId를 추출하는 메소드
    private String getMemberIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // 토큰에서 memberId 추출
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDTO> getSurveyQuestion(@PathVariable long questionId) {
        QuestionDTO questionDTO = surveyService.getSurveyQuestion(questionId);
        return ResponseEntity.ok(questionDTO);  // 질문을 성공적으로 반환
    }

    @PostMapping("/answer/{questionId}")
    public ResponseEntity<String> insertOrUpdateMemberAnswer(@PathVariable long questionId, @RequestBody MemberAnswerDTO memberAnswerDTO) {
        String memberId = getMemberIdFromToken();  // JWT 토큰에서 memberId 추출
        surveyService.insertOrUpdateMemberAnswer(memberId, questionId, memberAnswerDTO);

        // 모든 질문에 답변했는지 확인
        boolean allQuestionsAnswered = surveyService.areAllQuestionsAnswered(memberId);
        if (allQuestionsAnswered) {
            // 설문 상태를 'Y'로 업데이트
            surveyService.updateSurveyStatus(memberId, 'Y');
        }

        return ResponseEntity.status(201).body("Answer Inserted/Updated Successfully");
    }

    // 현재 사용자의 설문 상태를 확인하고 업데이트
    @GetMapping("/survey-status")
    public ResponseEntity<String> getSurveyStatus(@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();

        // 현재 설문 상태 반환
        String surveyStatus = surveyService.getSurveyStatus(memberId);
        return ResponseEntity.ok(surveyStatus);
    }

    // 설문 상태를 업데이트하는 메소드
    @PostMapping("/update-survey-status")
    public ResponseEntity<Void> updateSurveyStatus(@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();

        // 설문 상태 업데이트 로직 호출
        surveyService.updateSurveyStatus(memberId,'Y');

        return ResponseEntity.ok().build();
    }


}
