package com.example.spring.service;

import com.example.spring.domain.EditStockPortfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.StockHistory;
import com.example.spring.domain.MemberStockPortfolio;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.dto.MemberTotalStockPortfolioPowerDTO;
import com.example.spring.mapper.MarketHolidayMapper;
import com.example.spring.mapper.StockHistoryMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.KRXBusinessDayCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    private final MarketHolidayMapper marketHolidayMapper;
    private final StockHistoryMapper stockHistoryMapper;
    private final StockMapper stockMapper;

    @Autowired
    public StockServiceImpl(MarketHolidayMapper marketHolidayMapper, StockHistoryMapper stockHistoryMapper, StockMapper stockMapper) {
        this.marketHolidayMapper = marketHolidayMapper;
        this.stockHistoryMapper = stockHistoryMapper;
        this.stockMapper = stockMapper;
    }

    @Override
    public List<StockHistoryDTO> findByStockId(Long stockId) {
        List<String> marketHolidays;
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        String currentYear = today.format(DateTimeFormatter.ofPattern("yyyy"));

        marketHolidays = marketHolidayMapper.selectMarketHolidaysByYear(currentYear);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> holidays = marketHolidays.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .collect(Collectors.toSet());

        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDate nearestPrevBusinessDay = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate prevNthBusinessDay = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);

        List<StockHistory> stockHistoryList;
        DateTimeFormatter nonLineFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        stockHistoryList = stockHistoryMapper.findByStockId(stockId,
                prevNthBusinessDay.format(nonLineFormatter),
                nearestPrevBusinessDay.format(nonLineFormatter)
        );

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

    @Override
    public StockIndexDTO findIndexByStockId(Long stockId) {
        Stock stock = stockMapper.findByStockId(stockId);
        return new StockIndexDTO(stock.getMarketCapitalization(), stock.getEps(), stock.getPer(), stock.getBps(), stock.getPbr());
    }

    @Override
    public List<MemberStockPortfolio> getMemberStockPortfolio(String memberId) {
        List<MemberStockPortfolio> memberStockPortfolioList = stockMapper.getMemberStockPortfolio(memberId);
        return memberStockPortfolioList;
    }

    @Override
    public EditStockPortfolio getMemberStockPortfolioByDate(String memberId, Timestamp timestamp, Long stockId){
        return stockMapper.getMemberStockPortfolioByDate(memberId, timestamp, stockId);
    }

    @Override
    public MemberTotalStockPortfolioPowerDTO getMemberTotalStockPortfolio(String memberId) {
        // 같은 클래스 내의 getMemberStockPortfolio 메서드를 호출
        List<MemberStockPortfolio> memberStockPortfolioList = this.getMemberStockPortfolio(memberId);

        double totalInvestedAmount = 0.0; // 총 투자 금액
        double totalProfitLossAmount = 0.0; // 현재가 기준 손익금(평가손익)

        // for-each 루프를 통해 모든 totalInvestedAmount 값을 더함
        for (MemberStockPortfolio portfolio : memberStockPortfolioList) {
            totalInvestedAmount += portfolio.getTotalInvestedAmount(); // 값을 더함
            totalProfitLossAmount += portfolio.getTotalProfitLossAmount();
        }
        double totalProfitLossPercentage = ((totalProfitLossAmount / totalInvestedAmount)) * 100;

        MemberTotalStockPortfolioPowerDTO memberTotalStockPortfolioPowerDTO = new MemberTotalStockPortfolioPowerDTO();
        memberTotalStockPortfolioPowerDTO.setTotalInvestedAmount(totalInvestedAmount);
        memberTotalStockPortfolioPowerDTO.setTotalProfitLossAmount(totalProfitLossAmount);
        memberTotalStockPortfolioPowerDTO.setTotalProfitLossPercentage(totalProfitLossPercentage);
        return memberTotalStockPortfolioPowerDTO;
    }
}