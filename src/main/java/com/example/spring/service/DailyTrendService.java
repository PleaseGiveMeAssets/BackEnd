package com.example.spring.service;

import com.example.spring.dto.DailyTrendReportDTO;

public interface DailyTrendService {
    public DailyTrendReportDTO getDailyTrendInfo(String userId);
}
