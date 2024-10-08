package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.dto.DailyRecommendStockDTO;
import com.example.spring.dto.DailyStockDTO;
import com.example.spring.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Stock> stockList = stockMapper.selectListRecommendStockByUserId(userId, date);

        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo stockList : {}", stockList);
        }

        List<DailyStockDTO> dailyStockDTOList = new ArrayList<>();
        if (!stockList.isEmpty()) {
            stockList.stream().forEach(stockVO -> {
                DailyStockDTO dailyStockDTO = new DailyStockDTO();
                dailyStockDTO.setStockId(stockVO.getStockId());
                dailyStockDTO.setShortCode(stockVO.getShortCode());
                dailyStockDTO.setStockName(stockVO.getStockName());

                List<DailyRecommendStockDTO> dailyRecommendStockDTOList = new ArrayList<>();
                if (!stockVO.getRecommendStockList().isEmpty()) {
                    stockVO.getRecommendStockList().stream().forEach(recommendStock -> {
                        DailyRecommendStockDTO dailyRecommendStockDTO = new DailyRecommendStockDTO();
                        dailyRecommendStockDTO.setRecommendStockId(recommendStock.getRecommendStockId());
                        dailyRecommendStockDTO.setUserId(recommendStock.getUserId());
                        dailyRecommendStockDTO.setContent(recommendStock.getContent());
                        dailyRecommendStockDTOList.add(dailyRecommendStockDTO);
                    });
                }
                dailyStockDTO.setDailyRecommendStockDTOList(dailyRecommendStockDTOList);
                dailyStockDTOList.add(dailyStockDTO);
            });
        }
        return dailyStockDTOList;
    }
}
