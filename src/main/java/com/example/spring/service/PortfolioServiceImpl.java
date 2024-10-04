package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.dto.OrderSummaryDTO;
import com.example.spring.dto.PortfolioDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.OrderTypeStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public List<ForChartDTO> getOrderList(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<ForChartDTO> forChartDTOList = portfolioMapper.findByUserId(userId);

        log.info("ForChartDTOList : {}", forChartDTOList);
        log.info(System.getProperty("user.dir"));
        return forChartDTOList;
    }

    @Override
    public OrderSummaryDTO getOrderSummary(String userId, Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stockVO = stockMapper.findByStockId(stockId);
        if (stockVO == null)
            throw new IllegalArgumentException("Stock not found");

        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<Portfolio> portfolioVOList = portfolioMapper.findByUserIdAndStockId(userId, stockVO.getStockId());
        Long totalPrice = 0L;
        Long totalQuantity = 0L;
        if (portfolioVOList == null || portfolioVOList.isEmpty())
            throw new IllegalArgumentException("Portfolio not found");

        for (Portfolio order : portfolioVOList) {
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

        return portfolioMapper.update(orderId, portfolioDTO);
    }

    @Override
    public int createOrder(String userId, Long stockId, PortfolioDTO portfolioDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);

        String shortCode = stockMapper.findShortCodeByStockId(stockId);
        if(shortCode == null)
            return 0;
        return portfolioMapper.insert(userId, stockId, shortCode, portfolioDTO);
    }
}
