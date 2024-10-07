package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.mapper.MarketHolidayMapper;
import com.example.spring.util.KRXBusinessDayCalculator;
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
public class StockServiceTest {
    @Autowired
    private MarketHolidayMapper marketHolidayMapper;

    @Test
    public void businessDayTest() {
        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        List<String> marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear("2024");
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> holidays = marketHolidays.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .collect(Collectors.toSet());

        LocalDate nearestPrevBusinessDay = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate nthPrevBusinessDay = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);

        int businessDayDifference = businessDayCalculator.calculateBusinessDaysBetween(nthPrevBusinessDay, nearestPrevBusinessDay, holidays);
        assertTrue(businessDayDifference == 7);
    }

}
