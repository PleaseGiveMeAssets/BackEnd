package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerVO {
    private String UserId;
    private Long QuestionId;
    private Long QuestionOptionId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
