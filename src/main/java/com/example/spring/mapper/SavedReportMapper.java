package com.example.spring.mapper;

import com.example.spring.domain.DailyReport;
import com.example.spring.domain.SavedNews;
import com.example.spring.domain.SavedReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavedReportMapper {
    List<DailyReport> findAllSavedReportsByUserId(String userId);

    void save(SavedReport savedReport);

    void deleteByUserIdAndSavedReportId(@Param("userId") String userId, @Param("savedReportId") Long savedReportId);
}
