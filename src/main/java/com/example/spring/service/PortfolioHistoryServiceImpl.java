package com.example.spring.service;

import com.example.spring.dto.TotalStockInfoDTO;
import com.example.spring.mapper.MarketHolidayMapper;
import com.example.spring.mapper.PortfolioHistoryMapper;
import com.example.spring.util.KRXBusinessDayCalculator;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
    public List<TotalStockInfoDTO> getStockPortfolioInfo(String userId) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String currentYear = today.format(DateTimeFormatter.ofPattern("yyyy"));

        List<String> marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear(currentYear);

        if (log.isInfoEnabled()) {
            log.info("getStockPortfolioInfo marketHolidays: {}", marketHolidays);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Set<LocalDate> holidays = marketHolidays.stream()
                .map(holiday -> LocalDate.parse(holiday, formatter))
                .collect(Collectors.toSet());

        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDate startDate = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);
        LocalDate endDate = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        List<LocalDate> businessDayList = businessDayCalculator.getListBusinessDays(today.toLocalDate(), 7, holidays);

        String startDateFormat = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + MemberCodeEnum.START_TIME.getValue();
        String endDateFormat = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + MemberCodeEnum.END_TIME.getValue();

        List<TotalStockInfoDTO> totalStockInfoDTOList = portfolioHistoryMapper.selectListStockPortfolioByUserId(userId, startDateFormat, endDateFormat);

        if (totalStockInfoDTOList == null || totalStockInfoDTOList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getStockPortfolioInfo totalStockInfoDTOList : {}", totalStockInfoDTOList.toString());
        }

        businessDayList.forEach(businessDay -> {
            String businessDayFormat = businessDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            boolean isBusinessDay = totalStockInfoDTOList.stream().anyMatch(totalStockInfoDTO -> totalStockInfoDTO.getStockDate().equals(businessDayFormat));

            if (!isBusinessDay) {
                totalStockInfoDTOList.add(new TotalStockInfoDTO(businessDayFormat, 0L, 0L));
            }
        });
        totalStockInfoDTOList.sort(Comparator.comparing(TotalStockInfoDTO::getStockDate));

        return totalStockInfoDTOList;
    }
}
