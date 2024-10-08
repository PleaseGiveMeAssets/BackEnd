package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.dto.*;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.OrderTypeStatus;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioMapper portfolioMapper;
    private final StockMapper stockMapper;

    @Autowired
    public PortfolioServiceImpl(PortfolioMapper portfolioMapper, StockMapper stockMapper) {
        this.portfolioMapper = portfolioMapper;
        this.stockMapper = stockMapper;
    }

    @Override
    public List<ForChartDTO> getOrderList(String userId) {
        List<Stock> stockList = portfolioMapper.selectListPortfolioByUserId(userId);

        if (stockList.isEmpty()) {
            throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
        }

        if (log.isInfoEnabled()) {
            log.info("getOrderList stockList {}", stockList.toString());
        }

        List<ForChartDTO> forChartDTOList = new ArrayList<>();
        stockList.forEach(stock -> {
            ForChartDTO forChartDTO = new ForChartDTO();
            forChartDTO.setStockId(stock.getStockId());
            forChartDTO.setShortCode(stock.getShortCode());
            forChartDTO.setStockName(stock.getStockName());
            forChartDTO.setStockTradeStatus(stock.getStockTradeStatus());

            if (stock.getPortfolioList().isEmpty()) {
                throw new NoSuchElementException(ResultCodeEnum.NO_EXIST_DATA.getMessage());
            }

            Long totalBuyQuantity = stock.getPortfolioList().stream()
                    .filter(portfolio -> OrderTypeStatus.BUY.getCode() == portfolio.getOrderType())
                    .mapToLong(Portfolio::getQuantity)
                    .sum();

            Long totalSellQuantity = stock.getPortfolioList().stream()
                    .filter(portfolio -> OrderTypeStatus.SELL.getCode() == portfolio.getOrderType())
                    .mapToLong(Portfolio::getQuantity)
                    .sum();

            forChartDTO.setTotalQuantity(totalBuyQuantity - totalSellQuantity);
            forChartDTO.setTotalPrice((totalBuyQuantity - totalSellQuantity) * stock.getClosedPrice());
            forChartDTOList.add(forChartDTO);
        });
        return forChartDTOList;
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
        Stock stock = stockMapper.findByStockId(stockId);

        if (stock == null)
            throw new IllegalArgumentException("Stock not found");

        List<Portfolio> symbolTradeHistory = portfolioMapper.selectOrdersByUserIdAndStockId(userId, stock.getStockId());
        OrderSummaryDTO OrderSummary = makeSummary(stock, symbolTradeHistory, null);

        log.info("exampleDTOList : {}", OrderSummary);
        log.info(System.getProperty("user.dir"));
        return OrderSummary;
    }

    @Override
    public int updateOrder(Long orderId, OrderDTO orderDTO) {
        return portfolioMapper.update(orderId, orderDTO);
    }

    @Override
    public int createOrder(String userId, Long stockId, OrderDTO orderDTO) {
        String shortCode = stockMapper.findShortCodeByStockId(stockId);
        if (shortCode == null)
            return 0;
        return portfolioMapper.insert(userId, stockId, shortCode, orderDTO);
    }

    @Override
    public int deleteOrders(OrderDeleteDTO orderDeleteDTO) {
        return portfolioMapper.deleteOrdersByIds(orderDeleteDTO.getOrderId());
    }

    @Override
    public int deleteALlOrder(String userId, Long stockId) {
        return portfolioMapper.deleteAllOrder(userId, stockId);
    }

    @Override
    public OrderHistoryDTO getOrders(String userId, Long stockId) {
        Stock stock = stockMapper.findByStockId(stockId);
        if (stock == null)
            throw new IllegalArgumentException("Stock not found");

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
