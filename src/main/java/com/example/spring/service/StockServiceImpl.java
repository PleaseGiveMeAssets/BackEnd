package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.domain.StockHistory;
import com.example.spring.domain.UserStockPortfolio;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.dto.UserTotalStockPortfolioPowerDTO;
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
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockServiceImpl implements StockService{
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public StockServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<StockHistoryDTO> findByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MarketHolidayMapper marketHolidaymapper = sqlSession.getMapper(MarketHolidayMapper.class);

        String currentYear = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy"));
        List<String> marketHolidays = marketHolidaymapper.selectMarketHolidaysByYear(currentYear);
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> holidays = marketHolidays.stream()
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .collect(Collectors.toSet());

        KRXBusinessDayCalculator businessDayCalculator = new KRXBusinessDayCalculator();
        LocalDate nearestPrevBusinessDay = businessDayCalculator.getNearestPrevBusinessDay(today.toLocalDate(), holidays);
        LocalDate prevNthBusinessDay = businessDayCalculator.getNthPrevBusinessDay(today.toLocalDate(), 7, holidays);

        StockHistoryMapper stockHistoryMapper = sqlSession.getMapper(StockHistoryMapper.class);
        DateTimeFormatter nonLineFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        List<StockHistory> byStockId = stockHistoryMapper.findByStockId(stockId,
                prevNthBusinessDay.format(nonLineFormatter),
                nearestPrevBusinessDay.format(nonLineFormatter)
        );

        List<StockHistoryDTO> stockHistoryDTOList = new ArrayList<>();
        for (StockHistory stockHistory : byStockId) {
            stockHistoryDTOList.add(
                    new StockHistoryDTO(
                            stockHistory.getStockHistoryId(),
                            stockHistory.getOpenPrice(),
                            stockHistory.getClosedPrice(),
                            stockHistory.getHighPrice(),
                            stockHistory.getLowPrice()
                            ));
        }
        return stockHistoryDTOList;
    }
    @Override
    public StockIndexDTO findIndexByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stock = stockMapper.findByStockId(stockId);
        return new StockIndexDTO(stock.getMarketCapitalization(), stock.getEps(), stock.getPer(), stock.getBps(), stock.getPbr());
    }
    @Override
    public List<UserStockPortfolio> getUserStockPortfolio(String userId){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        List<UserStockPortfolio> userStockPortfolioList = stockMapper.getUserStockPortfolio(userId);
        return userStockPortfolioList;
    }

    @Override
    public UserTotalStockPortfolioPowerDTO getUserTotalStockPortfolio(String userId){
        // 같은 클래스 내의 getUserStockPortfolio 메서드를 호출
        List<UserStockPortfolio> userStockPortfolioList = this.getUserStockPortfolio(userId);

        double totalInvestedAmount = 0.0; // 총 투자 금액
        double totalProfitLossAmount = 0.0; // 현재가 기준 손익금(평가손익)

        // for-each 루프를 통해 모든 totalInvestedAmount 값을 더함
        for (UserStockPortfolio portfolio : userStockPortfolioList) {
            totalInvestedAmount += portfolio.getTotalInvestedAmount(); // 값을 더함
            totalProfitLossAmount += portfolio.getTotalProfitLossAmount();
        }
        double totalProfitLossPercentage = ((totalProfitLossAmount / totalInvestedAmount)-1) * 100;
        UserTotalStockPortfolioPowerDTO userTotalStockPortfolioPowerDTO = new UserTotalStockPortfolioPowerDTO();
        userTotalStockPortfolioPowerDTO.setTotalInvestedAmount(totalInvestedAmount);
        userTotalStockPortfolioPowerDTO.setTotalProfitLossAmount(totalProfitLossAmount);
        userTotalStockPortfolioPowerDTO.setTotalProfitLossPercentage(totalProfitLossPercentage);
        return userTotalStockPortfolioPowerDTO;

    }

}