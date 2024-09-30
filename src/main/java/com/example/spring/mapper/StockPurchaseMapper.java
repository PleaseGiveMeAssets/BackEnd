package com.example.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper
public interface StockPurchaseMapper {
     void savePortfolio(
             @Param("userId") String userId,
             @Param("stockName") String stockName,
             @Param("price") BigInteger price,
             @Param("quantity") int quantity,
             @Param("memo") String memo,
             @Param("orderType") char orderType,
             @Param("orderedAt") Timestamp orderedAt
     );
}
