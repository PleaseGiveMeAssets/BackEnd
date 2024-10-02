package com.example.spring.dto;

import lombok.Data;


@Data
public class QuestionOptionDTO {
    private int questionOptionId;
    private String content;
    private double score;


}