package com.example.spring.mapper;

import com.example.spring.domain.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    List<Notification> getNotificationsByMemberId(@Param("memberId") String memberId);

    int deleteNotificationById(@Param("notificationId") int notificationId, @Param("memberId") String memberId);

    int deleteAllNotificationsByMemberId(@Param("memberId") String memberId);

}
