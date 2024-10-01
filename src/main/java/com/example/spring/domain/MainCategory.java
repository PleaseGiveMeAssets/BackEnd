package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainCategory {
    private Long mainCategoryId;
    private String mainCategoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<SubCategory> subCategoryList;
}
