package com.example.spring.service;

import com.example.spring.dto.ProfileEditDTO;
import com.example.spring.mapper.ProfileEditMapper;
import com.example.spring.service.ProfileEditService;
import com.example.spring.vo.ProfileEditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileEditServiceImpl implements ProfileEditService {

    @Autowired
    private ProfileEditMapper profileEditMapper;

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

        // DTO -> VO 변환
        ProfileEditVO profileEditVO = new ProfileEditVO(
                profileEditDTO.getName(),
                profileEditDTO.getNickname(),
                profileEditDTO.getPhoneFirst(),
                profileEditDTO.getPhoneMiddle(),
                profileEditDTO.getPhoneLast(),
                profileEditDTO.getProfileImageUrl(),
                profileEditDTO.getBirthDate()
        );

        log.info("DTO -> VO 변환 완료 - 사용자 ID: {}, 변환된 VO: {}", userId, profileEditVO);

        // DB 업데이트 실행
        profileEditMapper.updateProfile(userId, profileEditVO);
        log.info("프로필 업데이트 완료 - 사용자 ID: {}", userId);
    }
}
