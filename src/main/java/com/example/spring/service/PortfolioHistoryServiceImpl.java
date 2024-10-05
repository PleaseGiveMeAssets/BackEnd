package com.example.spring.service;

import com.example.spring.dto.*;
import com.example.spring.mapper.PortfolioHistoryMapper;
import com.example.spring.mapper.UserMapper;
import com.example.spring.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PortfolioHistoryServiceImpl implements PortfolioHistoryService {
    private SqlSessionFactory sqlSessionFactory;

    public PortfolioHistoryServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Map<LocalDate, TotalStockInfoDTO>> saveStockPortfolioInfo() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioHistoryMapper portfolioHistoryMapper = sqlSession.getMapper(PortfolioHistoryMapper.class);

        // 모든 사용자 조회
        List<String> userIdList = portfolioHistoryMapper.selectAllUserIds();

        Map<String, Map<LocalDate, TotalStockInfoDTO>> totalStockInfoMap = new HashMap<>();

        for (String userId : userIdList) {
            // 해당 사용자의 모든 거래 내역 가져오기 (날짜 오름차순 정렬)
            List<OrderVO> orders = portfolioHistoryMapper.getOrdersByUserAndStock(userId);

            // 모든 거래 날짜 목록 추출 (날짜 오름차순, 중복 제거)
            TreeSet<LocalDate> allDates = orders.stream()
                    .map(order -> order.getOrderedAt().toLocalDateTime().toLocalDate())
                    .collect(Collectors.toCollection(TreeSet::new));
            log.info("allDate : {}",allDates.toString());

            // 주식 ID 목록 추출 (사용자가 보유한 모든 주식)
            Set<Integer> stockIdSet = orders.stream()
                    .map(OrderVO::getStockId)
                    .collect(Collectors.toSet());
            log.info("stockIdSet : {}", stockIdSet.toString());

            // 날짜별로 수익률을 저장하기 위한 맵 선언
            Map<LocalDate, TotalStockInfoDTO> dateTotalStockInfoMap = new HashMap<>();

            // 날짜별로 순회
            for (LocalDate date : allDates) {
                log.info("=====================  date:{}", date);
                TotalStockInfoDTO totalStockInfoDTO = new TotalStockInfoDTO();

                // 주식별로 누적 포지션을 저장하기 위한 맵
                Map<Integer, Integer> totalQuantityMap = new HashMap<>();
                Map<Integer, Long> totalBuyAmountMap = new HashMap<>();
                Map<Integer, Integer> totalBuyAveragePriceMap = new HashMap<>();
                Map<Integer, Long> totalProfitMap = new HashMap<>();

                // 해당 날짜까지의 모든 거래 내역 필터링
                List<OrderVO> ordersUpToDate = orders.stream()
                        .filter(order -> !order.getOrderedAt().toLocalDateTime().toLocalDate().isBefore(date))
                        .collect(Collectors.toList());
                log.info("ordersUpToDate : {}", ordersUpToDate);

                // 해당 날짜까지의 주식별 누적 포지션 계산
                for (Integer stockId : stockIdSet) {
                    log.info("===========stockId:{}", stockId);
                    int totalQuantity = totalQuantityMap.getOrDefault(stockId, 0);
                    int  totalBuyAveragePrice = totalBuyAveragePriceMap.getOrDefault(stockId, 0);
                    long totalBuyAmount = totalBuyAmountMap.getOrDefault(stockId, 0L);
                    long totalProfit = totalProfitMap.getOrDefault(stockId, 0L);

                    // 해당 주식의 거래 내역 필터링
                    List<OrderVO> stockOrders = ordersUpToDate.stream()
                            .filter(order -> order.getStockId() == stockId && order.getOrderedAt().toLocalDateTime().toLocalDate().equals(date))
                            .collect(Collectors.toList());
                    log.info("stockOrders : {}", stockOrders);

//                    로직 수정 필요
                    for (OrderVO order : stockOrders) {
                        log.info(order.toString());
                        if ('S' == (order.getOrderType())) { // 매도
                            log.info("======판매 발생======");

                            totalQuantity -= order.getQuantity();
                            log.info("totalQuantity(누적 수량) : {}", totalQuantity);
                            totalProfit += (order.getPrice() - totalBuyAveragePrice) * order.getQuantity();
                            log.info("totalProfit(수익률발생) : {}", totalProfit);
                            totalBuyAmount -= (long) totalBuyAveragePrice * order.getQuantity();
                            log.info("totalBuyAmount (총 매수 금액): {}", totalBuyAmount);

                        } else if ('B' == (order.getOrderType())) { // 매수
                            totalQuantity += order.getQuantity();
                            totalBuyAmount += order.getPrice() * order.getQuantity();
                            // 살때마다 매수 평균가 갱신
                            totalBuyAveragePrice = (int)(totalBuyAmount / totalQuantity);
                            log.info("======구매 발생======");
                            log.info("totalQuantity(누적 수량) : {}", totalQuantity);
                            log.info("totalBuyAmount(총 매수 금액) : {}", totalBuyAmount);
                            log.info("totalBuyAveragePrice(매수 평균가 갱신): {}", totalBuyAveragePrice);
                        }
                    }
                    // 누적 포지션 업데이트
                    totalQuantityMap.put(stockId, totalQuantity);
                    totalProfitMap.put(stockId, totalProfit);
                    totalBuyAveragePriceMap.put(stockId, totalBuyAveragePrice);
                    totalBuyAmountMap.put(stockId, totalBuyAmount);
                    log.info("totalQuantityMap : {}", totalQuantityMap.get(stockId));
                    log.info("totalBuyAmountMap : {}", totalBuyAmountMap.get(stockId));
                    log.info("totalBuyAveragePriceMap : {}", totalBuyAveragePriceMap.get(stockId));
                    log.info("totalProfitMap : {}", totalProfitMap.get(stockId));


                    if (totalQuantityMap.get(stockId) <= 0) {
                        continue; // 보유한 주식이 없으면 계산하지 않음
                    }
                    // 종가 가져오기
                    Integer closingPrice = portfolioHistoryMapper.getStockClosingPrice(stockId, date);
                    log.info("closingPrice:{}", closingPrice);

                    if (closingPrice == null) {
                        closingPrice = totalBuyAveragePriceMap.getOrDefault(stockId,0);

                    }

                    // 수익 및 수익률 계산
                    double profit = totalProfitMap.get(stockId)
                            + (closingPrice - totalBuyAveragePriceMap.get(stockId)) * totalQuantityMap.get(stockId);
                    double profitRate = (profit / totalBuyAmountMap.get(stockId)) *  100 ;
                    log.info("profit:{}", profit);
                    log.info("profitRate:{}", profitRate);

                    // 결과 저장 또는 처리
                    log.info("================수익률 저장 : profit = {}, profitRate = {}", profit, profitRate);

                }
                long totalAmount = totalBuyAmountMap.values().stream().mapToLong(Long::longValue).sum(); // 합계 계산
                log.info("********************** totalAmount : {}", totalAmount);
                totalStockInfoDTO.setTotalAmount(totalAmount);

                // Value 값들의 합을 구하기
                long totalProfit = totalProfitMap.values().stream().mapToLong(Long::longValue).sum(); // 합계 계산
                log.info("********************** totalProfit : {}", totalProfit);
                totalStockInfoDTO.setTotalProfit(totalProfit);
                dateTotalStockInfoMap.put(date, totalStockInfoDTO);
            }
            totalStockInfoMap.put(userId, dateTotalStockInfoMap);
        }
        return totalStockInfoMap;
    }
}
