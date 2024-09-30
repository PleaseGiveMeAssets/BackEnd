package com.example.spring.service;

import com.example.spring.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {

    // 특정 사용자의 알림 목록 불러오기 (DTO로 변환해서 반환)
    List<NotificationDTO> getNotificationsByUserId(String userId);

    // 특정 알림 삭제하기
    void deleteNotificationById(int notificationId, String userId);

    // 특정 사용자의 모든 알림 삭제하기
    void deleteAllNotificationsByUserId(String userId);
}
