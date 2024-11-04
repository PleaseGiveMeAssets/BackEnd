package com.example.spring.service;

import com.example.spring.dto.MemberProfileDTO;

public interface MemberProfileService {
    MemberProfileDTO getMemberProfile(String memberId);
}
