package com.example.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Mapper
public interface PortfolioPurchaseMapper {
     void savePortfolio(
             @Param("userId") String userId,
             @Param("stockName") String stockName,
             @Param("price") BigInteger price,
             @Param("quantity") int quantity,
             @Param("memo") String memo,
             @Param("orderType") String orderType,
             @Param("orderedAt") LocalDateTime orderedAt
     );
}
