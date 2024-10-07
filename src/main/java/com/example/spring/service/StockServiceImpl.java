package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.domain.StockHistory;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.mapper.MarketHolidayMapper;
import com.example.spring.mapper.StockHistoryMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.KRXBusinessDayCalculator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StockServiceImpl implements StockService {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public StockServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public StockIndexDTO findIndexByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stock = stockMapper.findByStockId(stockId);
        return new StockIndexDTO(stock.getMarketCapitalization(), stock.getEps(), stock.getPer(), stock.getBps(), stock.getPbr());
    }

    @Override
    public List<StockHistoryDTO> findByStockId(Long stockId) {
        List<String> marketHolidays;
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MarketHolidayMapper marketHolidayMapper = sqlSession.getMapper(MarketHolidayMapper.class);

            String currentYear = today.format(DateTimeFormatter.ofPattern("yyyy"));

            marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear(currentYear);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> holidays = marketHolidays.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .collect(Collectors.toSet());

        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDate nearestPrevBusinessDay = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate prevNthBusinessDay = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);

        List<StockHistory> stockHistoryList;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            StockHistoryMapper stockHistoryMapper = sqlSession.getMapper(StockHistoryMapper.class);
            DateTimeFormatter nonLineFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            stockHistoryList = stockHistoryMapper.findByStockId(stockId,
                    prevNthBusinessDay.format(nonLineFormatter),
                    nearestPrevBusinessDay.format(nonLineFormatter)
            );
        }

        return stockHistoryList.stream()
                .map(stockHistory -> new StockHistoryDTO(
                        stockHistory.getStockHistoryId(),
                        stockHistory.getOpenPrice(),
                        stockHistory.getClosedPrice(),
                        stockHistory.getHighPrice(),
                        stockHistory.getLowPrice()
                ))
                .collect(Collectors.toList());
    }
}