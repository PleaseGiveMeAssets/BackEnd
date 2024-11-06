package com.example.spring.dto;

import lombok.Data;

@Data
public class InvestmentTypeDTO {
    private Long investmentTypeId;
    private String investmentTypeName;
    private String content;
    private String stockAllocationContent;
    private String stockSelectionContent;
    private String riskManagementContent;

}
