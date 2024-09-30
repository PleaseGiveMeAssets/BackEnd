package com.example.spring.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionOptionVO {
    private int questionOptionId;
    private int questionId;
    private String content;
    private double score;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
