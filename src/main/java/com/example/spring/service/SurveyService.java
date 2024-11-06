package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.MemberAnswerDTO;

public interface SurveyService {


    QuestionDTO getSurveyQuestion(long questionId);

    void insertOrUpdateMemberAnswer(String memberId, long questionId, MemberAnswerDTO memberAnswerDTO);

    void updateSurveyStatus(String memberId, char surveyStatus);

    String getSurveyStatus(String memberId);

    // 추가: 모든 질문에 답변이 완료되었는지 확인하는 메서드
    boolean areAllQuestionsAnswered(String memberId);
}
