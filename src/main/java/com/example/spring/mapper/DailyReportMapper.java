package com.example.spring.mapper;

import com.example.spring.domain.DailyReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyReportMapper {
    List<DailyReport> selectDailyReportByUserId(String userId);
}
