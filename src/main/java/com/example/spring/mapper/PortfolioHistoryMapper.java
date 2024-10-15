package com.example.spring.mapper;

import com.example.spring.domain.Stock;
import com.example.spring.domain.TotalInvestedSumStockPortfolio;
import com.example.spring.dto.TotalStockInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PortfolioHistoryMapper {
    List<TotalStockInfoDTO> selectListStockPortfolioByUserId(@Param("userId") String userId, @Param("startDateFormat") String startDateFormat, @Param("endDateFormat") String endDateFormat);
    List<TotalInvestedSumStockPortfolio> getUserStockPortfolioTotalInvestedAmountByDate(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
