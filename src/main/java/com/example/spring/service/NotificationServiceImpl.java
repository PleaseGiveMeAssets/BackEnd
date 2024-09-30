package com.example.spring.service;

import com.example.spring.dto.NotificationDTO;
import com.example.spring.mapper.NotificationMapper;
import com.example.spring.vo.NotificationVO;
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
    public List<NotificationDTO> getNotificationsByUserId(String userId) {
        List<NotificationVO> notifications = notificationMapper.getNotificationsByUserId(userId);
        if (log.isInfoEnabled()){
            log.info("hi : {}", notifications.get(0).getMessageType());
        }
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotificationById(int notificationId, String userId) {
        notificationMapper.deleteNotificationById(notificationId, userId);
    }

    @Override
    public void deleteAllNotificationsByUserId(String userId) {
        notificationMapper.deleteAllNotificationsByUserId(userId);
    }

    // VO를 DTO로 변환하는 메서드
    private NotificationDTO convertToDTO(NotificationVO notificationVO) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notificationVO.getNotificationId());
        notificationDTO.setUserId(notificationVO.getUserId());
        notificationDTO.setMessage(notificationVO.getMessage());
        notificationDTO.setMessageType(notificationVO.getMessageType());
        notificationDTO.setReaded(notificationVO.getReaded());
        notificationDTO.setCreatedAt(notificationVO.getCreatedAt());
        notificationDTO.setUpdatedAt(notificationVO.getUpdatedAt());
        return notificationDTO;
    }
}
