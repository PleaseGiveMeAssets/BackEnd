package com.example.spring.service;

import com.example.spring.dto.ProfileEditDTO;
import com.example.spring.mapper.ProfileEditMapper;
import com.example.spring.vo.ProfileEditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileEditServiceImpl implements ProfileEditService {

    @Autowired
    private ProfileEditMapper profileEditMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ProfileEditDTO getProfile(String userId) {
        log.info("프로필 조회 시작 - 사용자 ID: {}", userId);

        // VO 객체를 받아서 DTO로 변환
        ProfileEditVO profileEditVO = profileEditMapper.getProfileByUserId(userId);
        if (profileEditVO == null) {
            log.warn("프로필 조회 실패 - 사용자 ID: {}", userId);
            return null;
        }

        log.info("프로필 조회 성공 - 사용자 ID: {}, 프로필 VO: {}", userId, profileEditVO);

        // VO -> DTO 변환
        ProfileEditDTO profileEditDTO = new ProfileEditDTO(
                profileEditVO.getName(),
                profileEditVO.getNickname(),
                profileEditVO.getPhoneFirst(),
                profileEditVO.getPhoneMiddle(),
                profileEditVO.getPhoneLast(),
                profileEditVO.getProfileImageUrl(),
                profileEditVO.getBirthDate()
        );

        log.info("VO -> DTO 변환 완료 - 사용자 ID: {}, 프로필 DTO: {}", userId, profileEditDTO);
        return profileEditDTO;
    }

    @Override
    public void updateProfile(String userId, ProfileEditDTO profileEditDTO) {
        log.info("프로필 업데이트 시작 - 사용자 ID: {}, 업데이트할 데이터: {}", userId, profileEditDTO);

        // 핸드폰 번호 마지막 부분 암호화 처리
        log.info("핸드폰 번호 마지막 부분 암호화 시작 - 원본: {}", profileEditDTO.getPhoneLast());
        String encryptedPhoneLast = passwordEncoder.encode(profileEditDTO.getPhoneLast());
        log.info("핸드폰 번호 마지막 부분 암호화 완료 - 암호화된 값: {}", encryptedPhoneLast);

        // DTO -> VO 변환 (여기서는 암호화된 값을 VO에만 설정)
        ProfileEditVO profileEditVO = new ProfileEditVO(
                profileEditDTO.getName(),
                profileEditDTO.getNickname(),
                profileEditDTO.getPhoneFirst(),
                profileEditDTO.getPhoneMiddle(),
                encryptedPhoneLast,  // 암호화된 값 사용
                profileEditDTO.getProfileImageUrl(),
                profileEditDTO.getBirthDate()
        );

        log.info("DTO -> VO 변환 완료 - 사용자 ID: {}, 변환된 VO: {}", userId, profileEditVO);

        // DB 업데이트 실행
        profileEditMapper.updateProfile(userId, profileEditVO);
        log.info("프로필 업데이트 완료 - 사용자 ID: {}", userId);
    }
}
