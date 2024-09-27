package com.example.spring.mapper;

import com.example.spring.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    int insert(PortfolioVO portfolioVO);
    int update(PortfolioVO portfolioVO);
    List<PortfolioVO> findByUserIdAndStockId(@Param("userId") String userId, @Param("stockId") Long stockId);
}
