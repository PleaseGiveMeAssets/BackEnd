package com.example.spring.util;

public enum StockTradeStatus {
    SUSPENDED('S'),
    ACTIVE('A');

    private final char statusCode;

    StockTradeStatus(char statusCode) {
        this.statusCode = statusCode;
    }

    public char getStatusCode() {
        return statusCode;
    }

    public static StockTradeStatus fromCode(char code) {
        for (StockTradeStatus status : StockTradeStatus.values()) {
            if (status.getStatusCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 코드 : " + code);
    }
}
