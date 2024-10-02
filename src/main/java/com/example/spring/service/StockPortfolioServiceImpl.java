package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.dto.OrderPortfolioDTO;
import com.example.spring.dto.StockPortfolioDTO;
import com.example.spring.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockPortfolioServiceImpl implements StockPortfolioService {
    private SqlSessionFactory sqlSessionFactory;

    public StockPortfolioServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<StockPortfolioDTO> getStockPortfolioInfo(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        List<Stock> stockList = stockMapper.selectListPortfolioByUserId(userId);

        if (log.isInfoEnabled()) {
            log.info("getStockPortfolioInfo stockList : {}", stockList);
        }

        List<StockPortfolioDTO> stockPortfolioDTOList = new ArrayList<>();
        if (!stockList.isEmpty()) {
            stockList.stream().forEach(stock -> {
                StockPortfolioDTO stockPortfolioDTO = new StockPortfolioDTO();
                stockPortfolioDTO.setStockId(stock.getStockId());
                stockPortfolioDTO.setShortCode(stock.getShortCode());

                List<OrderPortfolioDTO> orderPortfolioDTOList = new ArrayList<>();
                if (!stock.getPortfolioList().isEmpty()) {
                    stock.getPortfolioList().stream().forEach(portfolio -> {
                        OrderPortfolioDTO orderPortfolioDTO = new OrderPortfolioDTO();
                        orderPortfolioDTO.setPortfolioId(portfolio.getPortfolioId());
                        orderPortfolioDTO.setUserId(portfolio.getUserId());
                        orderPortfolioDTO.setPrice(portfolio.getPrice());
                        orderPortfolioDTO.setQuantity(portfolio.getQuantity());
                        orderPortfolioDTO.setOrderType(portfolio.getOrderType());
                        orderPortfolioDTOList.add(orderPortfolioDTO);
                    });
                }

                stockPortfolioDTO.setOrderPortfolioDTOList(orderPortfolioDTOList);
                stockPortfolioDTO.setStockName(stock.getStockName());
                stockPortfolioDTO.setStockTradeStatus(stock.getStockTradeStatus());
                stockPortfolioDTOList.add(stockPortfolioDTO);
            });
        }
        return stockPortfolioDTOList;
    }
}
