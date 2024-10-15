package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Stock;
import com.example.spring.dto.*;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.StockHistoryMapper;
import com.example.spring.mapper.StockMapper;
import com.example.spring.util.OrderTypeStatus;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioMapper portfolioMapper;
    private final StockMapper stockMapper;
    private final StockHistoryMapper stockHistoryMapper;

    @Autowired
    public PortfolioServiceImpl(PortfolioMapper portfolioMapper, StockMapper stockMapper, StockHistoryMapper stockHistoryMapper) {
        this.portfolioMapper = portfolioMapper;
        this.stockMapper = stockMapper;
        this.stockHistoryMapper = stockHistoryMapper;
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
            forChartDTOList.sort(Comparator.comparing(ForChartDTO::getTotalPrice).reversed());
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
        Long recentPrice = stockHistoryMapper.findRecentPriceByStockIdAndShortCode(stockId, stock.getShortCode());
        List<Portfolio> symbolTradeHistory = portfolioMapper.selectOrdersByUserIdAndStockId(userId, stock.getStockId());
        OrderSummaryDTO orderSummary = makeSummary(stock, symbolTradeHistory, recentPrice, null);
        log.info("exampleDTOList : {}", orderSummary);
        log.info(System.getProperty("user.dir"));
        return orderSummary;
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
        Long recentPrice = stockHistoryMapper.findRecentPriceByStockIdAndShortCode(stockId, stock.getShortCode());

        List<Portfolio> symbolTradeHistory = portfolioMapper.selectOrdersByUserIdAndStockId(userId, stock.getStockId());
        List<OrderDTO> orders = new ArrayList<>();
        OrderSummaryDTO orderSummary = makeSummary(stock, symbolTradeHistory, recentPrice, orders);

        Collections.reverse(orders);
        return new OrderHistoryDTO(
                orderSummary.getName(),
                stock.getShortCode(),
                orderSummary.getAvgPrice(),
                orderSummary.getTotalQuantity(),
                recentPrice,
                orders
        );
    }

    private OrderSummaryDTO makeSummary(Stock stock, List<Portfolio> symbolTradeHistory, Long recentPrice, List<OrderDTO> orderDTOList) {
        Long totalQuantity = 0L;
        Double avgPrice = 0.0;

        for (Portfolio order : symbolTradeHistory) {
            if (order.getOrderType() == 'B') {
                Double previousTotalCost = avgPrice * totalQuantity;

                totalQuantity += order.getQuantity();

                Double newTotalCost = previousTotalCost + (order.getPrice() * order.getQuantity());

                avgPrice = newTotalCost / totalQuantity;
            } else if (order.getOrderType() == 'S') {
                totalQuantity -= order.getQuantity();
            }
            if (orderDTOList != null) {
                orderDTOList.add(new OrderDTO(order.getPortfolioId(), order.getPrice(),
                        order.getQuantity(), order.getMemo(), order.getOrderType(), order.getOrderedAt(), order.getCreatedAt()));
            }
        }

        return new OrderSummaryDTO(stock.getStockName(), stock.getShortCode(), Math.round(avgPrice), totalQuantity, recentPrice);
    }
}
