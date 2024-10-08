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

    @GetMapping("/total-score/{userId}")
    public ResponseEntity<Integer> getTotalScore(@PathVariable String userId) {
        int totalScore = surveyResultService.getTotalScore(userId);
        return ResponseEntity.ok(totalScore);
    }

    @GetMapping("/investment-type/{userId}")
    public ResponseEntity<InvestmentTypeAnswerDTO> getInvestmentType(@PathVariable String userId) {

        log.info("Received userId: " + userId);

        InvestmentTypeAnswerDTO investmentTypeAnswerDTO = surveyResultService.getInvestmentType(userId);
        return ResponseEntity.ok(investmentTypeAnswerDTO);
    }

    @GetMapping("/investment-type/details")
    public ResponseEntity<InvestmentTypeDTO> getInvestmentTypeDetails(@AuthenticationPrincipal UserDetails userDetails) {
        InvestmentTypeDTO investmentTypeDTO = surveyResultService.getInvestmentTypeDetails(userDetails.getUsername());
        return ResponseEntity.ok(investmentTypeDTO);
    }

}
