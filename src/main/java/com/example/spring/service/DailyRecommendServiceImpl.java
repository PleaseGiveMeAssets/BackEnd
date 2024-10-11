package com.example.spring.service;

import com.example.spring.dto.DailyStockDTO;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class DailyRecommendServiceImpl implements DailyRecommendService {
    private final StockMapper stockMapper;

    @Autowired
    public DailyRecommendServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    /**
     * 일일추천종목 조회
     * <p>
     * 유저아이디와 날짜를 사용해 추천종목 리스트를 조회하는 메소드이다.
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public List<DailyStockDTO> getDailyRecommendStockInfo(String userId, String date) {
        List<DailyStockDTO> dailyStockDTOList = stockMapper.selectListRecommendStockByUserId(userId, date);

        if (dailyStockDTOList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo dailyStockDTOList : {}", dailyStockDTOList);
        }
        return dailyStockDTOList;
    }
}
