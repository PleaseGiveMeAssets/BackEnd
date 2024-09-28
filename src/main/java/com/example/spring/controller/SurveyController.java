package com.example.spring.controller;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.UserAnswerDTO;
import com.example.spring.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDTO> getSurveyQuestion(@PathVariable int questionId) {
        // Survey 질문을 데이터베이스에서 가져오기
        QuestionDTO questionDTO = surveyService.getSurveyQuestion(questionId);
        if (questionDTO != null) {
            return ResponseEntity.ok(questionDTO);  // 성공적으로 질문 반환
        } else {
            return ResponseEntity.notFound().build();  // 질문이 없으면 404 반환
        }
    }

    @PostMapping("/answer/{questionId}")
    public ResponseEntity<String> insertOrUpdateUserAnswer(@PathVariable int questionId, @RequestBody UserAnswerDTO userAnswerDTO) {

        // userId를 testUser1로 임시 설정!!
        String userId = "testUser1";

        int result = surveyService.insertOrUpdateUserAnswer(userId, questionId, userAnswerDTO);
        if (result > 0) {
            return ResponseEntity.status(201).body("Created/Updated");  // 생성 또는 수정 성공
        } else {
            return ResponseEntity.badRequest().body("Bad Request");  // 잘못된 요청
        }
    }
}
