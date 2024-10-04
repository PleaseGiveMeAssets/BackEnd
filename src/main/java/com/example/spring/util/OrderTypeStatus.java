package com.example.spring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeStatus {
    BUY('B', "매수"),   // 매수 (Buy)
    SELL('S', "매도");  // 매도 (Sell)

    private final char code;
    private final String description;

    public static OrderTypeStatus fromCode(char code) {
        for (OrderTypeStatus status : OrderTypeStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 코드 : " + code);
    }
}
