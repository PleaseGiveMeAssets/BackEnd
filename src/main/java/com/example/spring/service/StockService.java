package com.example.spring.service;

import com.example.spring.domain.EditStockPortfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.MemberStockPortfolio;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.dto.MemberTotalStockPortfolioPowerDTO;

import java.sql.Timestamp;
import java.util.List;

public interface StockService {
    List<StockHistoryDTO> findByStockId(Long stockId);
    StockIndexDTO findIndexByStockId(Long stockId);
    List<MemberStockPortfolio> getMemberStockPortfolio(String memberId);
    EditStockPortfolio getMemberStockPortfolioByDate(String memberId, Timestamp timestamp, Long stockId);
    MemberTotalStockPortfolioPowerDTO getMemberTotalStockPortfolio(String memberId);

}
