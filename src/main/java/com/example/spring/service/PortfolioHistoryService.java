package com.example.spring.service;

import com.example.spring.dto.TotalStockInfoDTO;

import java.time.LocalDate;
import java.util.Map;

public interface  PortfolioHistoryService {
    Map<String, TotalStockInfoDTO> getStockPortfolioInfo(String userId);
}
