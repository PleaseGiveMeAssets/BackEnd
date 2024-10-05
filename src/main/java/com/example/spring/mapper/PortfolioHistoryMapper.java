package com.example.spring.mapper;

import com.example.spring.dto.MyStockDTOTest;
import com.example.spring.dto.PortfolioHistoryDTO;
import com.example.spring.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface PortfolioHistoryMapper {
    // 사용자별 종목별 거래 내역 가져오기
    List<OrderVO> getOrdersByUserAndStock(@Param("userId") String userId);

    // 특정 종목의 종가 가져오기
    Integer getStockClosingPrice(@Param("stockId") int stockId, @Param("date") LocalDate date);

    // Portfolio_history에 데이터 삽입
    void insertPortfolioHistory(PortfolioHistoryDTO historyDTO);

    List<String> selectAllUserIds();

    MyStockDTOTest findByMyStockList(@Param("userId") String userId, @Param("stockName") String stockName);

}
