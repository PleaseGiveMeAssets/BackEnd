package com.example.spring.mapper;

import com.example.spring.domain.StockHistory;
import com.example.spring.dto.StockHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockHistoryMapper {
    List<StockHistory> findByStockId(Long stockId);
}
