package com.example.spring.mapper;

import com.example.spring.vo.ExampleVO;
import com.example.spring.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    List<OrderVO> findByUserId(String userId);
}
