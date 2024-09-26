package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportVO {
    private long dailyReportId;
    private String userId;
    private String recentTrendTitle;
    private String recentTrendContent;
    private String stockTrendTitle;
    private String stockTrendContent;
    private int kosdaqPrice;
    private int kosdaqProfitRate;
    private int kospiPrice;
    private int kospiProfitRate;
    private String dailyTrendSummarizedTitle;
    private String dailyTrendSummarizedContent;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
