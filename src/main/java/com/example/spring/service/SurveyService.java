package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;

public interface SurveyService {

    public QuestionDTO getSurveyQuestion(int questionId);
}
