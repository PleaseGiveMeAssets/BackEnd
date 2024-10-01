package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long NotificationId;
    private String UserId;
    private String message;
    private Character messageType;
    private Character readed;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
