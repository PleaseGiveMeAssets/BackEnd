package com.example.spring.mapper;

import com.example.spring.dto.MyStockDTO;
import com.example.spring.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<StockVO> findByStockName(@Param("stockName") String stockName);
    List<MyStockDTO> findMyAllStockByUserId(@Param("userId") String userId);
    List<MyStockDTO> findByMyStockList(@Param("userId") String userId, @Param("stockName") String stockName);
}
