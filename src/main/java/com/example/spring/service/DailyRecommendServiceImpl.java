package com.example.spring.service;

import com.example.spring.dto.StockDTO;
import com.example.spring.mapper.StockMapper;
import com.example.spring.vo.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DailyRecommendServiceImpl implements DailyRecommendService {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public DailyRecommendServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 일일추천종목 조회
     * <p>
     * 유저아이디와 날짜를 사용해 추천종목 리스트를 조회하는 메소드이다.
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public List<StockDTO> getDailyRecommendStockInfo(String userId, String date) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        List<StockVO> stockVOList = stockMapper.selectListRecommendStockByUserId(userId, date);
        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo stockVOList : {}", stockVOList);
        }

        List<StockDTO> stockDTOList = new ArrayList<>();
        if (!stockVOList.isEmpty()) {
            stockVOList.stream().forEach(stockVO -> {
                StockDTO stockDTO = new StockDTO();
                stockDTO.setStockId(stockVO.getStockId());
                stockDTO.setStandardCode(stockVO.getStandardCode());
                stockDTO.setSubCategoryId(stockVO.getSubCategoryId());
                stockDTO.setRecommendStockVOList(stockVO.getRecommendStockVOList());
                stockDTO.setStockName(stockVO.getStockName());
                stockDTO.setShortCode(stockVO.getShortCode());
                stockDTO.setStockExchangeMarket(stockVO.getStockExchangeMarket());
                stockDTO.setMarketCapitalization(stockVO.getMarketCapitalization());
                stockDTO.setStockTradeStatus(stockVO.getStockTradeStatus());
                stockDTO.setCreatedAt(stockVO.getCreatedAt());
                stockDTO.setUpdatedAt(stockVO.getUpdatedAt());
                stockDTOList.add(stockDTO);
            });
        }
        return stockDTOList;
    }
}
