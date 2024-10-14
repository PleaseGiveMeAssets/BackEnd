package com.example.spring.mapper;

import com.example.spring.domain.EditStockPortfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.UserStockPortfolio;
import com.example.spring.dto.DailyStockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface StockMapper {
    Stock findByStockId(Long stockId);

    int insert(Stock stock);

    String findShortCodeByStockId(Long stockId);

    List<DailyStockDTO> selectListRecommendStockByUserId(@Param("userId") String userId, @Param("date") String date);

    List<UserStockPortfolio> getUserStockPortfolio(@Param("userId") String userId);

    List<Stock> selectListPortfolioByUserId(String userId);

    EditStockPortfolio getUserStockPortfolioByDate(@Param("userId") String userId, @Param("endDate") Timestamp timestamp, @Param("stockId") Long stockId);
}
