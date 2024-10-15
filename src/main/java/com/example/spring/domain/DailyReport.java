package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {
    private Long dailyReportId;
    private String userId;
    private String recentTrendTitle;
    private String recentTrendContent;
    private String stockTrendTitle;
    private String stockTrendContent;
    private Double kosdaqPrice;
    private Double kosdaqProfitRate;
    private Double kospiPrice;
    private Double kospiProfitRate;
    private String dailyTrendSummarizedTitle;
    private String dailyTrendSummarizedContent;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<SavedReport> savedReportList;
}
