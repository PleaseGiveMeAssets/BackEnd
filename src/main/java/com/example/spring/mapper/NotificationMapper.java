package com.example.spring.mapper;

import com.example.spring.domain.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    List<Notification> getNotificationsByUserId(@Param("userId") String userId);

    int deleteNotificationById(@Param("notificationId") int notificationId, @Param("userId") String userId);

    int deleteAllNotificationsByUserId(@Param("userId") String userId);

}
