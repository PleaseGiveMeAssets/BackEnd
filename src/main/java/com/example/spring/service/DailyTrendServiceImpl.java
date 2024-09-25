package com.example.spring.service;

import com.example.spring.dto.DailyReportDTO;
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
    public DailyReportDTO getDailyTrendInfo(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DailyReportMapper dailyReportMapper = sqlSession.getMapper(DailyReportMapper.class);
        List<DailyReportVO> dailyReportVOList = dailyReportMapper.selectDailyReportByUserId(userId);
        if (log.isInfoEnabled()) {
            log.info("getDailyTrendInfo dailyReportVOList : {}", dailyReportVOList);
        }

        DailyReportDTO dailyReportDTO = new DailyReportDTO();
        if (!dailyReportVOList.isEmpty()) {
            dailyReportDTO.setDailyReportId(dailyReportVOList.get(0).getDailyReportId());
            dailyReportDTO.setUserId(dailyReportVOList.get(0).getUserId());
            dailyReportDTO.setRecentTrendTitle(dailyReportVOList.get(0).getRecentTrendTitle());
            dailyReportDTO.setRecentTrendContent(dailyReportVOList.get(0).getRecentTrendContent());
            dailyReportDTO.setStockTrendTitle(dailyReportVOList.get(0).getStockTrendTitle());
            dailyReportDTO.setStockTrendContent(dailyReportVOList.get(0).getStockTrendContent());
            dailyReportDTO.setKosdaqPrice(dailyReportVOList.get(0).getKosdaqPrice());
            dailyReportDTO.setKosdaqProfitRate(dailyReportVOList.get(0).getKosdaqProfitRate());
            dailyReportDTO.setKospiPrice(dailyReportVOList.get(0).getKospiPrice());
            dailyReportDTO.setKospiProfitRate(dailyReportVOList.get(0).getKospiProfitRate());
            dailyReportDTO.setDailyTrendSummarizedTitle(dailyReportVOList.get(0).getDailyTrendSummarizedTitle());
            dailyReportDTO.setDailyTrendSummarizedContent(dailyReportVOList.get(0).getDailyTrendSummarizedContent());
            dailyReportDTO.setCreatedAt(dailyReportVOList.get(0).getCreatedAt());
            dailyReportDTO.setUpdatedAt(dailyReportVOList.get(0).getUpdatedAt());
        }
        return dailyReportDTO;
    }
}
