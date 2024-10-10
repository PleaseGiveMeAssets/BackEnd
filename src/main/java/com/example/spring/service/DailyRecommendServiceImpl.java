package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.dto.DailyRecommendStockDTO;
import com.example.spring.dto.DailyStockDTO;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        List<Stock> stockList = stockMapper.selectListRecommendStockByUserId(userId, date);

        if (stockList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo stockList : {}", stockList);
        }

        List<DailyStockDTO> dailyStockDTOList = new ArrayList<>();

        stockList.forEach(stock -> {
            DailyStockDTO dailyStockDTO = new DailyStockDTO();
            dailyStockDTO.setStockId(stock.getStockId());
            dailyStockDTO.setShortCode(stock.getShortCode());
            dailyStockDTO.setStockName(stock.getStockName());

            List<DailyRecommendStockDTO> dailyRecommendStockDTOList = new ArrayList<>();

            if (stock.getRecommendStockList().isEmpty()) {
                throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
            }

            stock.getRecommendStockList().forEach(recommendStock -> {
                stock.getStockHistoryList().stream()
                        .filter(stockHistory -> stockHistory.getStockId().equals(recommendStock.getStockId()))
                        .findFirst()
                        .ifPresent(stockHistory -> {
                            DailyRecommendStockDTO dailyRecommendStockDTO = new DailyRecommendStockDTO();
                            dailyRecommendStockDTO.setRecommendStockId(recommendStock.getRecommendStockId());
                            dailyRecommendStockDTO.setContent(recommendStock.getContent());
                            dailyRecommendStockDTO.setPrice(recommendStock.getPrice());
                            dailyRecommendStockDTO.setChangeAmount(recommendStock.getPrice() - stockHistory.getClosedPrice());
                            dailyRecommendStockDTO.setChangeAmountRate(new BigDecimal(((recommendStock.getPrice() - stockHistory.getClosedPrice()) / recommendStock.getPrice().doubleValue())).setScale(2, RoundingMode.DOWN).doubleValue());
                            dailyRecommendStockDTO.setDay(LocalDateTime.ofInstant(recommendStock.getCreatedAt().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd")));
                            dailyRecommendStockDTOList.add(dailyRecommendStockDTO);
                        });
            });
            dailyStockDTO.setDailyRecommendStockDTOList(dailyRecommendStockDTOList);
            dailyStockDTOList.add(dailyStockDTO);
        });
        return dailyStockDTOList;
    }
}
