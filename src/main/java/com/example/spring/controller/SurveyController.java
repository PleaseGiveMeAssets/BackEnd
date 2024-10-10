package com.example.spring.controller;

import com.example.spring.dto.UserAnswerDTO;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/survey")
@Slf4j
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    // JWT 토큰에서 userId를 추출하는 메소드
    private String getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // 토큰에서 userId 추출
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDTO> getSurveyQuestion(@PathVariable long questionId) {
        QuestionDTO questionDTO = surveyService.getSurveyQuestion(questionId);
        return ResponseEntity.ok(questionDTO);  // 질문을 성공적으로 반환
    }

    @PostMapping("/answer/{questionId}")
    public ResponseEntity<String> insertOrUpdateUserAnswer(@PathVariable long questionId, @RequestBody UserAnswerDTO userAnswerDTO) {
//        String userId = "testUser1";  // 임시 userId 사용
        String userId = getUserIdFromToken();  // JWT 토큰에서 userId 추출
        surveyService.insertOrUpdateUserAnswer(userId, questionId, userAnswerDTO); // 성공 시 예외 처리로 인해 추가 확인 불필요
        return ResponseEntity.status(201).body("Answer Inserted/Updated Successfully");
    }


}
