package com.example.spring.dto;

import lombok.Data;

@Data
public class MemberAnswerDTO {
//    private String memberId;
    private Long questionId;
    private Long optionId;
}
