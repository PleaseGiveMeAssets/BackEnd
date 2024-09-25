package com.example.spring.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SavedReportVO {
    private final int savedReportId;
    private final String userId;
    private final int dailyReportId;
}
