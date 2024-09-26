package com.example.spring.mapper;

import com.example.spring.vo.DailyReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyReportMapper {
    List<DailyReportVO> selectDailyReportByUserId(String userId);
}
