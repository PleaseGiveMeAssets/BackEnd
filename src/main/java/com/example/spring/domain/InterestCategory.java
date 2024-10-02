package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestCategory {
    private Long interestCategoryId;
    private String userId;
    private Long subCategoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
