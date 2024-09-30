package com.example.spring.service;

import com.example.spring.dto.StockPurchaseDTO;
import com.example.spring.mapper.StockPurchaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockPurchaseServiceImpl implements StockPurchaseService {
    private final SqlSessionFactory sqlSessionFactory;

    // 사용자의 주식 정보 추가하기(매수)
    @Override
    public void saveStock(String userid, StockPurchaseDTO stockPurchaseDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockPurchaseMapper stockPurchaseMapper = sqlSession.getMapper(StockPurchaseMapper.class);
        stockPurchaseMapper.savePortfolio(
                userid,
                stockPurchaseDTO.getStockName(),
                stockPurchaseDTO.getPrice(),
                stockPurchaseDTO.getQuantity(),
                stockPurchaseDTO.getMemo(),
                'B',
                stockPurchaseDTO.getOrderedAt()
        );
    }
}
