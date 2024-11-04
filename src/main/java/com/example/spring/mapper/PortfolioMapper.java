package com.example.spring.mapper;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    int insert(@Param("memberId") String memberId, @Param("stockId") Long stockId, @Param("shortCode") String shortCode, @Param("orderDTO") OrderDTO orderDTO);

    int update(@Param("portfolioId") Long portfolioId, @Param("orderDTO") OrderDTO orderDTO);

    List<Portfolio> selectOrdersByMemberIdAndStockId(@Param("memberId") String memberId, @Param("stockId") Long stockId);

    // 특정 사용자에 대한 주문 정보를 가져오는 메서드
    List<Portfolio> selectOrdersByMemberId(@Param("memberId") String memberId);

    List<Stock> selectListPortfolioByMemberId(@Param("memberId") String memberId);

    int deleteOrdersByIds(List<Integer> orderIdList);

    int deleteAllOrder(@Param("memberId") String suerId, @Param("stockId") Long stockId);
}
