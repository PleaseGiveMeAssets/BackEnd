package com.example.spring.service;

import com.example.spring.dto.ProfileEditDTO;

public interface ProfileEditService {
    // 특정 사용자의 프로필 정보를 가져옴
    ProfileEditDTO getProfile(String userId);

    // 특정 사용자의 프로필 정보를 업데이트함
    void updateProfile(String userId, ProfileEditDTO profileEditDTO);
}
