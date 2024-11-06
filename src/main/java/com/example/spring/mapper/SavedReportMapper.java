package com.example.spring.mapper;

import com.example.spring.domain.DailyReport;
import com.example.spring.domain.SavedNews;
import com.example.spring.domain.SavedReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavedReportMapper {
    List<DailyReport> findAllSavedReportsByMemberId(String memberId);

    void save(SavedReport savedReport);

    void deleteByMemberIdAndSavedReportId(@Param("memberId") String memberId, @Param("savedReportId") Long savedReportId);
}
