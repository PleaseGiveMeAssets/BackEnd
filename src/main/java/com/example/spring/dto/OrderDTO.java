package com.example.spring.dto;

import com.example.spring.vo.OrderVO;
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
public class OrderDTO {
    private int orderId;
    private int userId;
    private int stockId;
    private BigDecimal price;
    private int quantity;
    private String memo;
    private char orderType;

    private LocalDateTime orderedAt;
    private LocalDateTime updatedAt;


    public static OrderDTO of(OrderVO orderVO) {
        return new OrderDTO(
                orderVO.getOrderId(),
                orderVO.getUserId(),
                orderVO.getStockId(),
                orderVO.getPrice(),
                orderVO.getQuantity(),
                orderVO.getMemo(),
                orderVO.getOrderType(),
                orderVO.getOrderedAt(),
                orderVO.getUpdatedAt()
        );
    }

}
