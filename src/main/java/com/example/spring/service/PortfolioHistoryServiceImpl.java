package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.StockHistory;
import com.example.spring.dto.TotalStockInfoDTO;
import com.example.spring.mapper.MarketHolidayMapper;
import com.example.spring.mapper.PortfolioHistoryMapper;
import com.example.spring.util.KRXBusinessDayCalculator;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.OrderTypeStatus;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PortfolioHistoryServiceImpl implements PortfolioHistoryService {
    private final MarketHolidayMapper marketHolidayMapper;
    private final PortfolioHistoryMapper portfolioHistoryMapper;

    @Autowired
    public PortfolioHistoryServiceImpl(MarketHolidayMapper marketHolidayMapper, PortfolioHistoryMapper portfolioHistoryMapper) {
        this.marketHolidayMapper = marketHolidayMapper;
        this.portfolioHistoryMapper = portfolioHistoryMapper;
    }

    @Override
    public Map<String, TotalStockInfoDTO> getStockPortfolioInfo(String userId) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String currentYear = today.format(DateTimeFormatter.ofPattern("yyyy"));

        List<String> marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear(currentYear);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Set<LocalDate> holidays = marketHolidays.stream()
                .map(holiday -> LocalDate.parse(holiday, formatter))
                .collect(Collectors.toSet());

        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDate startDate = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate endDate = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);

        String startDateFormat = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + MemberCodeEnum.START_TIME.getValue();
        String endDateFormat = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + MemberCodeEnum.END_TIME.getValue();

        List<Stock> stockList = portfolioHistoryMapper.selectListStockPortfolioByUserId(userId, startDateFormat, endDateFormat);

        if (stockList == null || stockList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getStockPortfolioInfo stockList : {}", stockList.toString());
        }

        Map<String, TotalStockInfoDTO> weeklyStockInfoMap = new HashMap<>();

        stockList.forEach(stock -> {
            // 매수 개수
            Map<String, Long> buyQuantity = stock.getStockHistoryList().stream()
                    .collect(Collectors.toMap(
                            StockHistory::getStockHistoryId,
                            stockHistory -> stock.getPortfolioList().stream()
                                    .filter(portfolio -> OrderTypeStatus.BUY.getCode() == portfolio.getOrderType())
                                    .filter(portfolio -> stock.getStockHistoryList().contains(stockHistory))
                                    .mapToLong(Portfolio::getQuantity)
                                    .sum()
                    ));

            // 매도 개수
            Map<String, Long> sellQuantity = stock.getStockHistoryList().stream()
                    .collect(Collectors.toMap(
                            StockHistory::getStockHistoryId,
                            stockHistory -> stock.getPortfolioList().stream()
                                    .filter(portfolio -> OrderTypeStatus.SELL.getCode() == portfolio.getOrderType())
                                    .filter(portfolio -> stock.getStockHistoryList().contains(stockHistory))
                                    .mapToLong(Portfolio::getQuantity)
                                    .sum()
                    ));

            // 매수 합산
            Map<String, Long> totalDailyBuyPrice = stock.getStockHistoryList().stream()
                    .collect(Collectors.toMap(
                            StockHistory::getStockHistoryId, // 각 StockHistoryId로 그룹화
                            stockHistory -> stock.getPortfolioList().stream()
                                    .filter(portfolio -> OrderTypeStatus.BUY.getCode() == portfolio.getOrderType()) // BUY 상태 필터링
                                    .filter(portfolio -> stock.getStockHistoryList().contains(stockHistory)) // StockHistory와 연결
                                    .mapToLong(portfolio -> (stockHistory.getClosedPrice() - portfolio.getPrice()) * (buyQuantity.get(stockHistory.getStockHistoryId()) - sellQuantity.get(stockHistory.getStockHistoryId()))) // 가격과 수량 곱한 값
                                    .sum() // 합산
                    ));

            // 매도 합산
            Map<String, Long> totalDailySellPrice = stock.getStockHistoryList().stream()
                    .collect(Collectors.toMap(
                            StockHistory::getStockHistoryId,
                            stockHistory -> stock.getPortfolioList().stream()
                                    .filter(portfolio -> OrderTypeStatus.SELL.getCode() == portfolio.getOrderType()) // SELL 상태 필터링
                                    .filter(portfolio -> stock.getStockHistoryList().contains(stockHistory)) // StockHistory와 연결
                                    .mapToLong(portfolio -> (stockHistory.getClosedPrice() - portfolio.getPrice()) * portfolio.getQuantity()) // 가격과 수량 곱한 값
                                    .sum() // 합산
                    ));

            // 매수와 매도를 합산
            Map<String, Long> totalDailyPrice = new HashMap<>();
            totalDailyBuyPrice.forEach((id, buyTotal) -> {
                Long sellTotal = totalDailySellPrice.getOrDefault(id, 0L); // 매도 값이 없을 경우 0L로 처리
                totalDailyPrice.put(id, buyTotal + sellTotal); // 매수 + 매도 합산
            });

            // 매도만 있는 경우도 추가 (매수가 없고 매도만 있는 경우)
            totalDailySellPrice.forEach((id, sellTotal) -> {
                totalDailyPrice.putIfAbsent(id, sellTotal); // 매수 합산이 없으면 매도 값만 추가
            });

            // 출력 예시
            totalDailyPrice.forEach((id, total) -> {
                weeklyStockInfoMap.put(id, new TotalStockInfoDTO(total, total / 100));
                System.out.println("stockHistoryId: " + id + ", 총 가격: " + total);
            });
        });
        return weeklyStockInfoMap;
    }
}
