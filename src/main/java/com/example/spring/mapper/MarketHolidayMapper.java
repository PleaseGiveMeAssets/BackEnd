package com.example.spring.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarketHolidayMapper {
    List<String> selectMarketHolidaysByYear(String year);
}
