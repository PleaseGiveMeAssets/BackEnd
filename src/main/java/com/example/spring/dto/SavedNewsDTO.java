package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class SavedNewsDTO {

    private int savedNewsId;      // 저장된 뉴스 ID
    private int newsId;           // 뉴스 ID
    private String userId;        // 사용자 ID
    private Timestamp createdAt;   // 생성 시간
    private Timestamp updatedAt;   // 업데이트 시간
}
