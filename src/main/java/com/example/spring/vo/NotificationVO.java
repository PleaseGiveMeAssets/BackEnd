package com.example.spring.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotificationVO {
    private final int notificationId;
    private final String userId;
    private final String message;
    private final boolean isRead; // 0 or 1로 표현
}
