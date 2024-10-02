package com.example.spring.mapper;

import com.example.spring.domain.Portfolio;
import com.example.spring.dto.PortfolioDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    int insert(@Param("userId")String userId, @Param("stockId") Long stockId, @Param("shortCode") String shortCode, @Param("portfolioDTO") PortfolioDTO portfolioDTO);
    int update(@Param("portfolioId") Long portfolioId, @Param("portfolioDTO") PortfolioDTO portfolioDTO);
    List<Portfolio> findByUserIdAndStockId(@Param("userId") String userId, @Param("stockId") Long stockId);
    // 특정 사용자에 대한 주문 정보를 가져오는 메서드
    List<Portfolio> selectOrdersByUserId(@Param("userId") String userId);
}
