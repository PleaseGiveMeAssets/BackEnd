package com.example.spring.service;

import com.example.spring.dto.InvestmentTypeDTO;
import com.example.spring.mapper.SurveyResultMapper;
import com.example.spring.service.SurveyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyResultServiceImpl implements SurveyResultService {

    private final SurveyResultMapper surveyResultMapper;

    @Autowired
    public SurveyResultServiceImpl(SurveyResultMapper surveyResultMapper) {
        this.surveyResultMapper = surveyResultMapper;
    }

    @Override
    public InvestmentTypeDTO getInvestmentTypeByScore(String userId) {
        // 총 점수 계산
        int totalScore = surveyResultMapper.getTotalScore(userId);

        // 점수에 맞는 투자 유형 반환
        return surveyResultMapper.getInvestmentTypeByScore(totalScore);
    }
}
