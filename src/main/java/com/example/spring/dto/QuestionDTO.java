package com.example.spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private Long questionId;
    private String content;
    private List<QuestionOptionDTO> options; // 클라이언트에 보낼 데이터만 포함


}
