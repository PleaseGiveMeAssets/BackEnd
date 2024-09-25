package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SelectedTermsDTO {

    private int selectedTermsId;      // 선택된 약관 ID
    private int termOfUseId;          // 이용 약관 ID
    private String userId;            // 사용자 ID
    private Timestamp createdAt;       // 생성 시간
    private Timestamp updatedAt;       // 업데이트 시간

}

