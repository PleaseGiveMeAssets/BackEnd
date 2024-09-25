package com.example.spring.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileEditDTO {

    private String userId; // 사용자 ID
    private String nickName; // 닉네임
    private String profileImageUrl; // 프로필 이미지 URL
    private Date birthDate; // 생년월일
    private String email; // 이메일
}
