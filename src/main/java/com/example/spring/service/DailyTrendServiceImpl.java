package com.example.spring.service;

import com.example.spring.domain.DailyReport;
import com.example.spring.dto.DailyTrendReportDTO;
import com.example.spring.mapper.DailyReportMapper;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class DailyTrendServiceImpl implements DailyTrendService {
    private final DailyReportMapper dailyReportMapper;

    @Autowired
    public DailyTrendServiceImpl(DailyReportMapper dailyReportMapper) {
        this.dailyReportMapper = dailyReportMapper;
    }

    @Override
    public DailyTrendReportDTO getDailyTrendInfo(String userId) {
        List<DailyReport> dailyReportList = dailyReportMapper.selectDailyReportByUserId(userId);

        if (dailyReportList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getDailyTrendInfo dailyReportList : {}", dailyReportList);
        }

        DailyTrendReportDTO dailyTrendReportDTO = new DailyTrendReportDTO();
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
        dailyTrendReportDTO.setHour(getLeftHour(dailyReportList.get(0).getCreatedAt()));
        return dailyTrendReportDTO;
    }

    private String getLeftHour(Timestamp timestamp) {
        LocalDateTime now = LocalDateTime.ofInstant(Timestamp.from(Instant.now()).toInstant(), ZoneId.systemDefault());

        LocalDateTime nextDay = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()).plusDays(1);

        long secondsDifference = ChronoUnit.SECONDS.between(now, nextDay);

        return Long.toString(secondsDifference / 3600);
    }
}
