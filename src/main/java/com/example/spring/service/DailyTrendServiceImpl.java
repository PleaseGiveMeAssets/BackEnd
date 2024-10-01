package com.example.spring.service;

import com.example.spring.domain.DailyReport;
import com.example.spring.dto.DailyTrendReportDTO;
import com.example.spring.mapper.DailyReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DailyTrendServiceImpl implements DailyTrendService {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public DailyTrendServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public DailyTrendReportDTO getDailyTrendInfo(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DailyReportMapper dailyReportMapper = sqlSession.getMapper(DailyReportMapper.class);
        List<DailyReport> dailyReportList = dailyReportMapper.selectDailyReportByUserId(userId);
        if (log.isInfoEnabled()) {
            log.info("getDailyTrendInfo dailyReportList : {}", dailyReportList);
        }

        DailyTrendReportDTO dailyTrendReportDTO = new DailyTrendReportDTO();
        if (!dailyReportList.isEmpty()) {
            dailyTrendReportDTO.setDailyReportId(dailyReportList.get(0).getDailyReportId());
            dailyTrendReportDTO.setUserId(dailyReportList.get(0).getUserId());
            dailyTrendReportDTO.setRecentTrendTitle(dailyReportList.get(0).getRecentTrendTitle());
            dailyTrendReportDTO.setRecentTrendContent(dailyReportList.get(0).getRecentTrendContent());
            dailyTrendReportDTO.setStockTrendTitle(dailyReportList.get(0).getStockTrendTitle());
            dailyTrendReportDTO.setStockTrendContent(dailyReportList.get(0).getStockTrendContent());
            dailyTrendReportDTO.setKosdaqPrice(dailyReportList.get(0).getKosdaqPrice());
            dailyTrendReportDTO.setKosdaqProfitRate(dailyReportList.get(0).getKosdaqProfitRate());
            dailyTrendReportDTO.setKospiPrice(dailyReportList.get(0).getKospiPrice());
            dailyTrendReportDTO.setKospiProfitRate(dailyReportList.get(0).getKospiProfitRate());
            dailyTrendReportDTO.setDailyTrendSummarizedTitle(dailyReportList.get(0).getDailyTrendSummarizedTitle());
            dailyTrendReportDTO.setDailyTrendSummarizedContent(dailyReportList.get(0).getDailyTrendSummarizedContent());
            dailyTrendReportDTO.setCreatedAt(dailyReportList.get(0).getCreatedAt());
        }
        return dailyTrendReportDTO;
    }
}
