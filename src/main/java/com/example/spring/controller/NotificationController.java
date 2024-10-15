package com.example.spring.controller;

import com.example.spring.dto.NotificationDTO;
import com.example.spring.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("알림 목록 조회 요청 - userId: {}", userDetails.getUsername());
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userDetails.getUsername());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // 특정 알림 삭제하기
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotificationById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int notificationId) {
        log.info("알림 삭제 요청 - userId: {}, notificationId: {}", userDetails.getUsername(), notificationId);
        notificationService.deleteNotificationById(notificationId, userDetails.getUsername());
        return new ResponseEntity<>("알림이 삭제되었습니다.", HttpStatus.OK);
    }

    // 특정 사용자의 모든 알림 삭제하기
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllNotificationsByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("알림 전체 삭제 요청 - userId: {}", userDetails.getUsername());
        notificationService.deleteAllNotificationsByUserId(userDetails.getUsername());
        return new ResponseEntity<>("모든 알림이 삭제되었습니다.", HttpStatus.OK);
    }
}
