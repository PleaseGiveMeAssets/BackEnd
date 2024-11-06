package com.example.spring.service;

import com.example.spring.domain.TotalInvestedSumStockPortfolio;
import com.example.spring.dto.StockPortfolioDTO;
import com.example.spring.dto.TotalStockInfoDTO;

import java.util.List;

public interface PortfolioHistoryService {
    List<TotalStockInfoDTO> getStockPortfolioInfo(String memberId);
    List<TotalInvestedSumStockPortfolio> getMemberStockPortfolioTotalInvestedAmountByDate(String memberId);
    List<StockPortfolioDTO> getStockPortfolioInfoByDate(String memberId);
}
