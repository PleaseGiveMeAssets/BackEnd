package com.example.spring.dto;

import lombok.Data;

@Data
public class UserAnswerDTO {
    private String userId;//
    private int questionId;
    private int optionId;
}