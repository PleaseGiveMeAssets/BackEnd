package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SavedReportDTO {

    private int savedReportId;   // 저장된 리포트 ID
    private String userId;       // 사용자 ID
    private int dailyReportId;   // 일일 리포트 ID
    private Timestamp createdAt;  // 생성 시간
    private Timestamp updatedAt;  // 업데이트 시간
}
