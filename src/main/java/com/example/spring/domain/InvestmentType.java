package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentType {
    private Long investmentTypeId;
    private String investmentTypeName;
    private String content;
    private String stockAllocationContent;
    private String stockSelectionContent;
    private String riskManagementContent;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
