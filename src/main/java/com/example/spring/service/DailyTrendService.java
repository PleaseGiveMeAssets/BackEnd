package com.example.spring.service;

import com.example.spring.dto.DailyReportDTO;

public interface DailyTrendService {
    public DailyReportDTO getDailyTrendInfo(String userId);
}
