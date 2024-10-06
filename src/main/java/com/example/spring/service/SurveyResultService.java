package com.example.spring.service;

import com.example.spring.dto.InvestmentTypeDTO;

public interface SurveyResultService {

    // 사용자의 총 점수를 계산하고 투자 유형을 반환하는 메서드
    InvestmentTypeDTO getInvestmentTypeByScore(String userId);
}
