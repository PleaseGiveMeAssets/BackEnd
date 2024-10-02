package com.example.spring.dto;

import lombok.Data;


@Data
public class QuestionOptionDTO {
    private Long questionOptionId;
    private String content;
    private double score;


}