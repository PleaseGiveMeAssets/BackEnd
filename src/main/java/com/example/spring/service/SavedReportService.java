package com.example.spring.service;

import com.example.spring.domain.DailyReport;
import com.example.spring.dto.SavedReportDTO;

import java.util.List;

public interface SavedReportService {
    List<DailyReport> getSavedReports(String memberId);
    void saveReport(Long dailyReportId, String memberId);
    void deleteReport(String memberId,Long savedReportId);
}
