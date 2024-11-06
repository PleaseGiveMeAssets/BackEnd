package com.example.spring.mapper;

import com.example.spring.domain.EditStockPortfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.MemberStockPortfolio;
import com.example.spring.dto.DailyStockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface StockMapper {
    Stock findByStockId(Long stockId);

    int insert(Stock stock);

    String findShortCodeByStockId(Long stockId);

    List<DailyStockDTO> selectListRecommendStockByMemberId(@Param("memberId") String memberId, @Param("date") String date);

    List<MemberStockPortfolio> getMemberStockPortfolio(@Param("memberId") String memberId);

    List<Stock> selectListPortfolioByMemberId(String memberId);

    EditStockPortfolio getMemberStockPortfolioByDate(@Param("memberId") String memberId, @Param("endDate") Timestamp timestamp, @Param("stockId") Long stockId);
}
