package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.mapper.StockMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public StockIndexDTO findIndexByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stock = stockMapper.findByStockId(stockId);
        return new StockIndexDTO(stock.getMarketCapitalization(), stock.getEps(), stock.getPer(), stock.getBps(), stock.getPbr());
    }
}
