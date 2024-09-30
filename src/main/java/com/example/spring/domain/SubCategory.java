package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    private Long subCategoryId;
    private Long mainCategoryId;
    private String subCategoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    List<InterestCategory> interestCategoryList;
    List<Stock> stockList;
}
