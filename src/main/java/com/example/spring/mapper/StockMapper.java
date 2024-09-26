package com.example.spring.mapper;

import com.example.spring.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMapper {
    List<StockVO> selectListRecommendStockByUserId(@Param("userId") String userId, @Param("date") String date);
}
