package com.example.spring.mapper;

import com.example.spring.domain.StockHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockHistoryMapper {
    List<StockHistory> findByStockId(@Param("stockId") Long stockId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    Long findRecentPriceByStockIdAndShortCode(@Param("stockId") Long stockId, @Param("shortCode")String shortCode);
}
