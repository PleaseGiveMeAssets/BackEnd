package com.example.spring.service;

import com.example.spring.dto.SavedReportDTO;

import java.util.List;

public interface SavedReportService {
    List<SavedReportDTO> getSavedReports(String userId);
    void saveReport(SavedReportDTO savedReportDto);
    void deleteReport(int savedReportId);
}
