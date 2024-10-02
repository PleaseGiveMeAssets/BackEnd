package com.example.spring.dto;

import lombok.Data;

@Data
public class TermsOfUseDTO {
    private int termOfUseId;          // 이용 약관 ID
    private String title;              // 제목
    private String content;            // 내용
    private String required;           // 필수 여부 (0: 비필수, 1: 필수)
}
