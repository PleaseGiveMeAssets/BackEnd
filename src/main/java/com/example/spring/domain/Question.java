package com.example.spring.domain;

import com.example.spring.vo.MemberAnswerVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Long questionId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private MemberAnswerVO memberAnswerVO;
    private List<QuestionOption> questionOptionList;
}
