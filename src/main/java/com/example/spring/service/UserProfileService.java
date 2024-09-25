package com.example.spring.service;

import com.example.spring.dto.UserDTO;
import com.example.spring.vo.UserVO;

public interface UserProfileService {
    UserDTO getUserProfile(String userId);
}
