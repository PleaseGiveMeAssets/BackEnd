package com.example.spring.domain;

import com.example.spring.vo.UserAnswerVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption {
    private Long questionOptionId;
    private Long questionId;
    private String content;
    private Double score;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserAnswerVO userAnswerVO;
}
