package com.example.spring.mapper;

import com.example.spring.domain.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMapper {
    List<Stock> selectListRecommendStockByUserId(@Param("userId") String userId, @Param("date") String date);

    List<Stock> selectListPortfolioByUserId(String userId);
}
