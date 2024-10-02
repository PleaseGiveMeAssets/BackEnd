package com.example.spring.mapper;

import com.example.spring.domain.Portfolio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface PortfolioMapper {
    // 특정 사용자에 대한 주문 정보를 가져오는 메서드
    List<Portfolio> selectOrdersByUserId(@Param("userId") String userId);
}
