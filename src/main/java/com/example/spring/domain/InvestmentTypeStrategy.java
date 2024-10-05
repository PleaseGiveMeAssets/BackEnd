package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentTypeStrategy {
    private Long investmentTypeStrategyId;
    private Long investmentTypeId;
    private String stockAllocationContent;
    private String stockSelectionContent;
    private String riskManagementContent;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
