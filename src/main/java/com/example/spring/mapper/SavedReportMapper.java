package com.example.spring.mapper;

import com.example.spring.vo.SavedReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavedReportMapper {
    List<SavedReportVO> getSavedReportsByUserId(String userId);
    void insertSavedReport(SavedReportVO savedReportVO);
    void deleteSavedReport(int savedReportId);
}
