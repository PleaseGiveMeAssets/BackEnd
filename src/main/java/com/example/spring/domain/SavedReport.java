package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedReport {
    private Long savedReportId;
    private String userId;
    private Long dailyReportId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
