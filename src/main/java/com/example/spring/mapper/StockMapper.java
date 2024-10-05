package com.example.spring.mapper;

import com.example.spring.domain.Stock;
import com.example.spring.dto.StockIndexDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMapper {
    Stock findByStockId(Long stockId);
    int insert(Stock stock);
    String findShortCodeByStockId(Long stockId);
    List<Stock> selectListPortfolioByUserId(String userId);
    List<Stock> selectListRecommendStockByUserId(@Param("userId") String userId, @Param("date") String date);
}
