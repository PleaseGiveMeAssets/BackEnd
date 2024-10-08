package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.UserAnswerDTO;

public interface SurveyService {


    QuestionDTO getSurveyQuestion(long questionId);

    void insertOrUpdateUserAnswer(String userId, long questionId, UserAnswerDTO userAnswerDTO);


}
