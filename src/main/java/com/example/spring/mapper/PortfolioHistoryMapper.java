package com.example.spring.mapper;

import com.example.spring.domain.Stock;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PortfolioHistoryMapper {
    List<Stock> selectListStockPortfolioByUserId(@Param("userId") String userId, @Param("startDateFormat") String startDateFormat, @Param("endDateFormat") String endDateFormat);
}
