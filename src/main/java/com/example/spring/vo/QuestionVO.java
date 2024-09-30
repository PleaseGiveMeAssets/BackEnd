package com.example.spring.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionVO {
    private int questionId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
