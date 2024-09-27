package com.example.spring.service;

import com.example.spring.dto.PortfolioDTO;
import com.example.spring.dto.OrderSummaryDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.OrderTypeStatus;
import com.example.spring.vo.PortfolioVO;
import com.example.spring.vo.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PortfolioServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public OrderSummaryDTO getOrderSummary(String userId, Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        StockVO stockVO = stockMapper.findByStockId(stockId);
        if (stockVO == null)
            throw new IllegalArgumentException("Stock not found");

        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<PortfolioVO> portfolioVOList = portfolioMapper.findByUserIdAndStockId(userId, stockVO.getStockId());
        Long totalPrice = 0L;
        Long totalQuantity = 0L;
        if (portfolioVOList == null || portfolioVOList.isEmpty())
            throw new IllegalArgumentException("Portfolio not found");

        for (PortfolioVO order : portfolioVOList) {
            if (OrderTypeStatus.fromCode(order.getOrderType()) == OrderTypeStatus.BUY) {
                totalPrice += order.getPrice() * order.getQuantity();
                totalQuantity += order.getQuantity();
            } else {
                totalPrice -= order.getPrice() * order.getQuantity();
                totalQuantity -= order.getQuantity();
            }
        }
        OrderSummaryDTO orderSummaryDTO = new OrderSummaryDTO(stockVO.getStockName(), totalPrice / totalQuantity, totalQuantity);
        log.info("exampleDTOList : {}", orderSummaryDTO);
        log.info(System.getProperty("user.dir"));
        return orderSummaryDTO;
    }

    @Override
    public int updateOrder(Long orderId, PortfolioDTO portfolioDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);

        PortfolioVO portfolioVO = new PortfolioVO(orderId, null, null, null, portfolioDTO.getPrice(), portfolioDTO.getQuantity(),
                portfolioDTO.getMemo(), portfolioDTO.getOrderType().getCode(), portfolioDTO.getOrderedAt(),
                null, null);
        return portfolioMapper.update(portfolioVO);
    }

    @Override
    public int createOrder(String userId, Long stockId, PortfolioDTO portfolioDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);

        String shortCode = stockMapper.findShortCodeByStockId(stockId);

        PortfolioVO portfolioVO = buildOrderVO(userId, shortCode, portfolioDTO);

        return portfolioMapper.insert(portfolioVO);
    }

    private PortfolioVO buildOrderVO(String userId, String shortCode, PortfolioDTO portfolioDTO) {
        return new PortfolioVO(null, userId, 1L, shortCode,
                portfolioDTO.getPrice(), portfolioDTO.getQuantity(),
                portfolioDTO.getMemo(), portfolioDTO.getOrderType().getCode(),
                portfolioDTO.getOrderedAt(), null, null);
    }
}
