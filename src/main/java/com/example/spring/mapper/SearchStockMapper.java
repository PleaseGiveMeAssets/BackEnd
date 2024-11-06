package com.example.spring.mapper;

import com.example.spring.dto.StockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchStockMapper {
    List<StockDTO> findByStockName(@Param("stockName") String stockName);
}
