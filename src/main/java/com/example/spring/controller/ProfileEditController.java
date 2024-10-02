package com.example.spring.controller;

import com.example.spring.dto.ProfileEditDTO;
import com.example.spring.service.ProfileEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/profile_edit")
@Slf4j
public class ProfileEditController {

    @Autowired
    private ProfileEditService profileEditService;

    // 사용자 ID로 프로필 정보를 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileEditDTO> getProfile(@PathVariable String userId) {
        log.info("프로필 조회 요청 - 사용자 ID: {}", userId);
        ProfileEditDTO profile = profileEditService.getProfile(userId);
        if (profile != null) {
            log.info("프로필 조회 완료 - 사용자 ID: {}", userId);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            log.warn("프로필 조회 실패 - 사용자 ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // 사용자 ID로 프로필 정보를 업데이트
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateProfile(@PathVariable String userId, @RequestBody ProfileEditDTO profileEditDTO) {
        log.info("프로필 업데이트 요청 - 사용자 ID: {}, 수정할 정보: {}", userId, profileEditDTO);
        try {
            profileEditService.updateProfile(userId, profileEditDTO);
            log.info("프로필 업데이트 완료 - 사용자 ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("프로필 업데이트 실패 - 사용자 ID: {}, 오류: {}", userId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
