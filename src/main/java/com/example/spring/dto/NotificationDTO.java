package com.example.spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {

    private int notificationId;   // 알림 ID
    private String userId;        // 사용자 ID
    private String message;       // 알림 메시지
    private char messageType;    // 알림 메시지 타입 (P: 주가변동 R: 리포트 S: 추천종목)
    private String readed;       // 읽음 여부 (Y: 읽음, N: 읽지 않음)
    private Timestamp createdAt;   // 생성 시간
    private Timestamp updatedAt;   // 업데이트 시간
}
