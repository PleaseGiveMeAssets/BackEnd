package com.example.spring.dto;

import com.example.spring.vo.PortfolioVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {
    private int orderId;
    private int userId;
    private int stockId;
    private BigDecimal price;
    private int quantity;
    private String memo;
    private char orderType;

    private LocalDateTime orderedAt;
    private LocalDateTime updatedAt;


    public static PortfolioDTO of(PortfolioVO portfolioVO) {
        return new PortfolioDTO(
                portfolioVO.getOrderId(),
                portfolioVO.getUserId(),
                portfolioVO.getStockId(),
                portfolioVO.getPrice(),
                portfolioVO.getQuantity(),
                portfolioVO.getMemo(),
                portfolioVO.getOrderType(),
                portfolioVO.getOrderedAt(),
                portfolioVO.getUpdatedAt()
        );
    }

}
