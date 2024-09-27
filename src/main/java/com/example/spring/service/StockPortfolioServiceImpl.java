package com.example.spring.service;

import com.example.spring.dto.OrderPortfolioDTO;
import com.example.spring.dto.StockPortfolioDTO;
import com.example.spring.mapper.StockMapper;
import com.example.spring.vo.StockVO;
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
        List<StockVO> stockVOList = stockMapper.selectListPortfolioByUserId(userId);

        List<StockPortfolioDTO> stockPortfolioDTOList = new ArrayList<>();
        if (!stockVOList.isEmpty()) {
            stockVOList.stream().forEach(stockVO -> {
                StockPortfolioDTO stockPortfolioDTO = new StockPortfolioDTO();
                stockPortfolioDTO.setStockId(stockVO.getStockId());
                stockPortfolioDTO.setShortCode(stockVO.getShortCode());

                List<OrderPortfolioDTO> orderPortfolioDTOList = new ArrayList<>();
                if (!stockVO.getPortfolioVOList().isEmpty()) {
                    stockVO.getPortfolioVOList().stream().forEach(portfolioVO -> {
                        OrderPortfolioDTO orderPortfolioDTO = new OrderPortfolioDTO();
                        orderPortfolioDTO.setPortfolioId(portfolioVO.getPortfolioId());
                        orderPortfolioDTO.setUserId(portfolioVO.getUserId());
                        orderPortfolioDTO.setPrice(portfolioVO.getPrice());
                        orderPortfolioDTO.setQuantity(portfolioVO.getQuantity());
                        orderPortfolioDTO.setOrderType(portfolioVO.getOrderType());
                        orderPortfolioDTOList.add(orderPortfolioDTO);
                    });
                }

                stockPortfolioDTO.setOrderPortfolioDTOList(orderPortfolioDTOList);
                stockPortfolioDTO.setStockName(stockVO.getStockName());
                stockPortfolioDTO.setStockTradeStatus(stockVO.getStockTradeStatus());
                stockPortfolioDTOList.add(stockPortfolioDTO);
            });
        }
        return stockPortfolioDTOList;
    }
}
