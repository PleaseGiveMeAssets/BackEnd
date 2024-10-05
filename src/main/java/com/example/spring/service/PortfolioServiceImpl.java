package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.dto.ForChartDTO;
import com.example.spring.dto.OrderDTO;
import com.example.spring.dto.OrderDeleteDTO;
import com.example.spring.dto.OrderHistoryDTO;
import com.example.spring.dto.OrderSummaryDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.OrderTypeStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PortfolioServiceImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    @Override
    public Map<String, List<ForChartDTO>> getOrderList(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        Map<String, List<ForChartDTO>> forchartDTOListMap = new HashMap<>();
        List<ForChartDTO> forChartDTOList = portfolioMapper.findByUserId(userId);
        forchartDTOListMap.put(userId, forChartDTOList);
        log.info("ForChartDTOList : {}", forChartDTOList);
        log.info(System.getProperty("user.dir"));
        return forchartDTOListMap;
    }
    /**
     * 포트폴리오 특정 종목 조회
     * <p>
     * 사용자가 해당 종목을 얼만큼 보유하고 있는지 조회하는 메소드이다.
     *
     * @param userId
     * @param stockId
     * @return PortfolioSummaryDTO
     */
    @Override
    public OrderSummaryDTO getOrderSummary(String userId, Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stock = stockMapper.findByStockId(stockId);
        if (stock == null)
            throw new IllegalArgumentException("Stock not found");

        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<Portfolio> symbolTradeHistory = portfolioMapper.selectOrdersByUserIdAndStockId(userId, stock.getStockId());
        OrderSummaryDTO OrderSummary = makeSummary(stock, symbolTradeHistory, null);

        log.info("exampleDTOList : {}", OrderSummary);
        log.info(System.getProperty("user.dir"));
        return OrderSummary;
    }

    @Override
    public int updateOrder(Long orderId, OrderDTO orderDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);

        return portfolioMapper.update(orderId, orderDTO);
    }

    @Override
    public int createOrder(String userId, Long stockId, OrderDTO orderDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);

        String shortCode = stockMapper.findShortCodeByStockId(stockId);
        if(shortCode == null)
            return 0;
        return portfolioMapper.insert(userId, stockId, shortCode, orderDTO);
    }

    @Override
    public int deleteOrders(OrderDeleteDTO orderDeleteDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        return portfolioMapper.deleteOrdersByIds(orderDeleteDTO.getOrderId());
    }

    @Override
    public int deleteALlOrder(String userId, Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        return portfolioMapper.deleteAllOrder(userId, stockId);
    }

    @Override
    public OrderHistoryDTO getOrders(String userId, Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        Stock stock = stockMapper.findByStockId(stockId);
        if (stock == null)
            throw new IllegalArgumentException("Stock not found");

        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<Portfolio> symbolTradeHistory = portfolioMapper.selectOrdersByUserIdAndStockId(userId, stock.getStockId());
        List<OrderDTO> orders = new ArrayList<>();
        OrderSummaryDTO orderSummary = makeSummary(stock, symbolTradeHistory, orders);

        return new OrderHistoryDTO(orderSummary.getName(),
                orderSummary.getPrice(),
                orderSummary.getQuantity(),
                orders
        );
    }

    private OrderSummaryDTO makeSummary(Stock stock, List<Portfolio> symbolTradeHistory, List<OrderDTO> orderDTOList) {
        Long totalPrice = 0L;
        Long totalQuantity = 0L;
        if (symbolTradeHistory == null || symbolTradeHistory.isEmpty())
            throw new IllegalArgumentException("Portfolio not found");

        for (Portfolio order : symbolTradeHistory) {
            if (OrderTypeStatus.fromCode(order.getOrderType()) == OrderTypeStatus.BUY) {
                totalPrice += order.getPrice() * order.getQuantity();
                totalQuantity += order.getQuantity();
            } else {
                totalPrice -= order.getPrice() * order.getQuantity();
                totalQuantity -= order.getQuantity();
            }

            if (orderDTOList != null) {
                orderDTOList.add(new OrderDTO(order.getPortfolioId(), order.getPrice(),
                        order.getQuantity(), order.getMemo(), order.getOrderType(), order.getOrderedAt()));
            }
        }
        return new OrderSummaryDTO(stock.getStockName(), totalPrice / totalQuantity, totalQuantity);
    }
}
