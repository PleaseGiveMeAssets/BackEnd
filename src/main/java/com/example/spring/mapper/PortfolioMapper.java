package com.example.spring.mapper;

import com.example.spring.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    List<OrderVO> findByUserId(@Param("userId") String userId);
}
