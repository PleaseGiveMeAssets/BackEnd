package com.example.spring.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserAnswerVO {
    private String userId;
    private int questionId;
    private int questionOptionId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
