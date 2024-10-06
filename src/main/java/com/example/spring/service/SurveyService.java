package com.example.spring.service;

import com.example.spring.domain.InvestmentType;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.UserAnswerDTO;

public interface SurveyService {


    QuestionDTO getSurveyQuestion(long questionId);

    int insertOrUpdateUserAnswer(String userId, long questionId, UserAnswerDTO userAnswerDTO);

    // 추가된 메서드: 사용자의 총 점수를 계산
    int getTotalScore(String userId);

}
