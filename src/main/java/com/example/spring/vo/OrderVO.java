package com.example.spring.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderVO {

    private int orderId;          // 주문 ID
    private String userId;        // 사용자 ID
    private int stockId;          // 주식 ID
    private String standardCode;  // 표준 코드
    private long price;           // 가격
    private int quantity;         // 수량
    private String memo;          // 메모
    private char orderType;       // 주문 타입
    private LocalDateTime orderedAt; // 주문 일시
    private LocalDateTime createdAt; // 생성 일시
    private LocalDateTime updatedAt; // 수정 일시
}
