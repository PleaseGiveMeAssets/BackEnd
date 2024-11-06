package com.example.spring.service;

import com.example.spring.domain.Notification;
import com.example.spring.dto.NotificationDTO;
import com.example.spring.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<NotificationDTO> getNotificationsByMemberId(String memberId) {
        List<Notification> notifications = notificationMapper.getNotificationsByMemberId(memberId);
        if (log.isInfoEnabled()) {
            log.info("hi : {}", notifications.get(0).getMessageType());
        }
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotificationById(int notificationId, String memberId) {
        notificationMapper.deleteNotificationById(notificationId, memberId);
    }

    @Override
    public void deleteAllNotificationsByMemberId(String memberId) {
        notificationMapper.deleteAllNotificationsByMemberId(memberId);
    }

    // VO를 DTO로 변환하는 메서드
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notification.getNotificationId());
        notificationDTO.setMemberId(notification.getMemberId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setMessageType(notification.getMessageType());
        notificationDTO.setReaded(notification.getReaded());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        notificationDTO.setUpdatedAt(notification.getUpdatedAt());
        return notificationDTO;
    }
}
