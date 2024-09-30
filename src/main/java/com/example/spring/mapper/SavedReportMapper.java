package com.example.spring.mapper;

import com.example.spring.domain.SavedReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavedReportMapper {
    List<SavedReport> getSavedReportsByUserId(String userId);

    void insertSavedReport(SavedReport savedReport);

    void deleteSavedReport(int savedReportId);
}
