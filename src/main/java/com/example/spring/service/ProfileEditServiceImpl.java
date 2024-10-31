package com.example.spring.service;

import com.example.spring.dto.ProfileEditDTO;
import com.example.spring.mapper.ProfileEditMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileEditServiceImpl implements ProfileEditService {

    @Autowired
    private ProfileEditMapper profileEditMapper;

    @Autowired
    private EncryptionService encryptionService;


    @Override
    public ProfileEditDTO getProfile(String memberId) {
        log.info("프로필 조회 시작 - 사용자 ID: {}", memberId);

        // DTO로 조회
        ProfileEditDTO profileEditDTO = profileEditMapper.getProfileByMemberId(memberId);
        if (profileEditDTO == null) {
            log.warn("프로필 조회 실패 - 사용자 ID: {}", memberId);
            return null;
        }

        // 핸드폰 번호 마지막 부분 복호화
        log.info("핸드폰 번호 마지막 부분 복호화 시작 - 암호화된 값: {}", profileEditDTO.getPhoneLast());
        String decryptedPhoneLast = encryptionService.decrypt(profileEditDTO.getPhoneLast());
        profileEditDTO.setPhoneLast(decryptedPhoneLast);
        log.info("핸드폰 번호 마지막 부분 복호화 완료 - 복호화된 값: {}", decryptedPhoneLast);

        log.info("프로필 조회 성공 - 사용자 ID: {}, 프로필 DTO: {}", memberId, profileEditDTO);
        return profileEditDTO;
    }

    @Override
    public void updateProfile(String memberId, ProfileEditDTO profileEditDTO) {
        log.info("프로필 업데이트 시작 - 사용자 ID: {}, 업데이트할 데이터: {}", memberId, profileEditDTO);

        // 핸드폰 번호 마지막 부분 암호화 처리
        log.info("핸드폰 번호 마지막 부분 암호화 시작 - 원본: {}", profileEditDTO.getPhoneLast());
        String encryptedPhoneLast = encryptionService.encrypt(profileEditDTO.getPhoneLast());
        profileEditDTO.setPhoneLast(encryptedPhoneLast);
        log.info("핸드폰 번호 마지막 부분 암호화 완료 - 암호화된 값: {}", encryptedPhoneLast);

        // DB 업데이트 실행
        profileEditMapper.updateProfile(memberId, profileEditDTO);
        log.info("프로필 업데이트 완료 - 사용자 ID: {}", memberId);
    }
}
