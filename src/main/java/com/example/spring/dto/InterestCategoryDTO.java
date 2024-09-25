package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InterestCategoryDTO {

    private String userId;            // 사용자 ID
    private int mainCategoryId;       // 주 카테고리 ID
    private String mainCategoryName;   // 주 카테고리 이름
    private int subCategoryId;         // 하위 카테고리 ID
    private String subCategoryName;    // 하위 카테고리 이름
    private Timestamp createdAt;        // 생성 시간
    private Timestamp updatedAt;        // 업데이트 시간
}
