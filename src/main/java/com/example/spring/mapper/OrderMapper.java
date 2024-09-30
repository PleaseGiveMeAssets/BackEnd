package com.example.spring.mapper;

import com.example.spring.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    // 특정 사용자에 대한 주문 정보를 가져오는 메서드
    List<PortfolioVO> selectOrdersByUserId(@Param("userId") String userId);
}
