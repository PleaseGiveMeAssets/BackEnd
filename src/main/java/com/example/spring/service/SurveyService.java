package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.UserAnswerDTO;

public interface SurveyService {


    QuestionDTO getSurveyQuestion(long questionId);

    void insertOrUpdateUserAnswer(String userId, long questionId, UserAnswerDTO userAnswerDTO);

    void updateSurveyStatus(String userId, char surveyStatus);

    String getSurveyStatus(String userId);

    // 추가: 모든 질문에 답변이 완료되었는지 확인하는 메서드
    boolean areAllQuestionsAnswered(String userId);
}
