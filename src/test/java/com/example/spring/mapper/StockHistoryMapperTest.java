package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.domain.StockHistory;
import com.example.spring.util.KRXBusinessDayCalculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Slf4j
public class StockHistoryMapperTest {
    @Autowired
    private StockHistoryMapper stockHistoryMapper;
    @Autowired
    private MarketHolidayMapper marketHolidayMapper;
    @Test
    public void selectStockHistoryByStockId(){
        Long stockId = 27491L;
        List<String> marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear("2024");
        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> holidays = marketHolidays.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .collect(Collectors.toSet());

        LocalDate nearestPrevBusinessDay = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate prevNthBusinessDay = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);
        DateTimeFormatter nonLineFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        log.info("prevNthBusinessDayFormat: {}", prevNthBusinessDay.format(nonLineFormatter));
        log.info("nearestPrevBusinessDayFormat: {}", nearestPrevBusinessDay.format(nonLineFormatter));
        List<StockHistory> stockHistories = stockHistoryMapper.findByStockId(stockId,
                prevNthBusinessDay.format(nonLineFormatter),
                nearestPrevBusinessDay.format(nonLineFormatter)
        );
        for (StockHistory stockHistory : stockHistories) {
            log.info("stockHistories = {}", stockHistory.getStockHistoryId());
        }
        assertTrue(!stockHistories.isEmpty());
    }
}
