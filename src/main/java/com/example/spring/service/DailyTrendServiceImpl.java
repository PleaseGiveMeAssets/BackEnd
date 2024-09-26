package com.example.spring.service;

import com.example.spring.dto.DailyTrendReportDTO;
import com.example.spring.mapper.DailyReportMapper;
import com.example.spring.vo.DailyReportVO;
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
        List<DailyReportVO> dailyReportVOList = dailyReportMapper.selectDailyReportByUserId(userId);
        if (log.isInfoEnabled()) {
            log.info("getDailyTrendInfo dailyReportVOList : {}", dailyReportVOList);
        }

        DailyTrendReportDTO dailyTrendReportDTO = new DailyTrendReportDTO();
        if (!dailyReportVOList.isEmpty()) {
            dailyTrendReportDTO.setDailyReportId(dailyReportVOList.get(0).getDailyReportId());
            dailyTrendReportDTO.setUserId(dailyReportVOList.get(0).getUserId());
            dailyTrendReportDTO.setRecentTrendTitle(dailyReportVOList.get(0).getRecentTrendTitle());
            dailyTrendReportDTO.setRecentTrendContent(dailyReportVOList.get(0).getRecentTrendContent());
            dailyTrendReportDTO.setStockTrendTitle(dailyReportVOList.get(0).getStockTrendTitle());
            dailyTrendReportDTO.setStockTrendContent(dailyReportVOList.get(0).getStockTrendContent());
            dailyTrendReportDTO.setKosdaqPrice(dailyReportVOList.get(0).getKosdaqPrice());
            dailyTrendReportDTO.setKosdaqProfitRate(dailyReportVOList.get(0).getKosdaqProfitRate());
            dailyTrendReportDTO.setKospiPrice(dailyReportVOList.get(0).getKospiPrice());
            dailyTrendReportDTO.setKospiProfitRate(dailyReportVOList.get(0).getKospiProfitRate());
            dailyTrendReportDTO.setDailyTrendSummarizedTitle(dailyReportVOList.get(0).getDailyTrendSummarizedTitle());
            dailyTrendReportDTO.setDailyTrendSummarizedContent(dailyReportVOList.get(0).getDailyTrendSummarizedContent());
            dailyTrendReportDTO.setCreatedAt(dailyReportVOList.get(0).getCreatedAt());
        }
        return dailyTrendReportDTO;
    }
}
