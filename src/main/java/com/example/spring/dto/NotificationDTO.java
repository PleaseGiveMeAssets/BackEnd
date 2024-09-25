package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {

    private int notificationId;   // 알림 ID
    private String userId;        // 사용자 ID
    private String message;       // 알림 메시지
    private int Read;           // 읽음 여부 (0: 읽지 않음, 1: 읽음)
    private Timestamp createdAt;   // 생성 시간
    private Timestamp updatedAt;   // 업데이트 시간
}
