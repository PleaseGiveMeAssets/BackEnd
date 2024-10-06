package com.example.spring.controller;

import com.example.spring.dto.InvestmentTypeDTO;
import com.example.spring.service.SurveyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/survey-result")
public class SurveyResultController {

    private final SurveyResultService surveyResultService;

    @Autowired
    public SurveyResultController(SurveyResultService surveyResultService) {
        this.surveyResultService = surveyResultService;
    }

    // 총 점수를 기반으로 설문 결과를 반환
    @GetMapping("/investment-type/{userId}")
    public ResponseEntity<InvestmentTypeDTO> getInvestmentTypeByScore(@PathVariable String userId) {
        InvestmentTypeDTO investmentTypeDTO = surveyResultService.getInvestmentTypeByScore(userId);
        return ResponseEntity.ok(investmentTypeDTO);
    }
}
