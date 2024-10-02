package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyTrendReportDTO {
    private Long dailyReportId;
    private String userId;
    private String recentTrendTitle;
    private String recentTrendContent;
    private String stockTrendTitle;
    private String stockTrendContent;
    private Integer kosdaqPrice;
    private Integer kosdaqProfitRate;
    private Integer kospiPrice;
    private Integer kospiProfitRate;
    private String dailyTrendSummarizedTitle;
    private String dailyTrendSummarizedContent;
    private Timestamp createdAt;
}
