package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.UserAnswerDTO;

public interface SurveyService {


    QuestionDTO getSurveyQuestion(int questionId);

    int insertOrUpdateUserAnswer(String userId, int questionId, UserAnswerDTO userAnswerDTO);

}
