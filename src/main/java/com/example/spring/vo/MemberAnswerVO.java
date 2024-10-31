package com.example.spring.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberAnswerVO {
    private String memberId;
    private Long questionId;
    private Long questionOptionId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
