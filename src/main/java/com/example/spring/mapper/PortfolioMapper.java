package com.example.spring.mapper;

import com.example.spring.dto.ForChartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    List<ForChartDTO> findByUserId(@Param("userId") String userId);
}
