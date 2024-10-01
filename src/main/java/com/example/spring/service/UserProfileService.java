package com.example.spring.service;

import com.example.spring.dto.UserDTO;

public interface UserProfileService {
    UserDTO getUserProfile(String userId);
}
