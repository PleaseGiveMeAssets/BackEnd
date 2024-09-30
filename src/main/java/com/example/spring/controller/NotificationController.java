package com.example.spring.controller;

import com.example.spring.dto.NotificationDTO;
import com.example.spring.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 특정 사용자의 알림 목록 불러오기 (DTO로 반환)
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable String userId) {
        log.info("알림 목록 조회 요청 - userId: {}", userId);
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // 특정 알림 삭제하기
    @DeleteMapping("/{userId}/{notificationId}")
    public ResponseEntity<String> deleteNotificationById(@PathVariable String userId, @PathVariable int notificationId) {
        log.info("알림 삭제 요청 - userId: {}, notificationId: {}", userId, notificationId);
        notificationService.deleteNotificationById(notificationId, userId);
        return new ResponseEntity<>("알림이 삭제되었습니다.", HttpStatus.OK);
    }

    // 특정 사용자의 모든 알림 삭제하기
    @DeleteMapping("/all/{userId}")
    public ResponseEntity<String> deleteAllNotificationsByUserId(@PathVariable String userId) {
        log.info("알림 전체 삭제 요청 - userId: {}", userId);
        notificationService.deleteAllNotificationsByUserId(userId);
        return new ResponseEntity<>("모든 알림이 삭제되었습니다.", HttpStatus.OK);
    }
}
