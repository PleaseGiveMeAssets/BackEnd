package com.example.spring.controller;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/survey")
@Slf4j
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getSurveyQuestion(@PathVariable int questionId) {
        // Survey 질문을 데이터베이스에서 가져오기
        QuestionDTO questionDTO = surveyService.getSurveyQuestion(questionId);
        if (questionDTO != null) {
            return ResponseEntity.ok(questionDTO);  // 성공적으로 질문 반환
        } else {
            return ResponseEntity.notFound().build();  // 질문이 없으면 404 반환
        }
    }

}
