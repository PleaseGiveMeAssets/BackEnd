package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentType {
    private Long investmentTypeId;
    private String userId;
    private String investmentTypeName;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private InvestmentTypeStrategy investmentTypeStrategy;
}
