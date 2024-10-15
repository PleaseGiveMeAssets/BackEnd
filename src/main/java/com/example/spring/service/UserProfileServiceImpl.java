package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.User;
import com.example.spring.dto.UserDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper userMapper;
    private final PortfolioMapper portfolioMapper;

    @Autowired
    public UserProfileServiceImpl(UserMapper userMapper, PortfolioMapper portfolioMapper) {
        this.userMapper = userMapper;
        this.portfolioMapper = portfolioMapper;
    }

    @Override
    public UserDTO getUserProfile(String userId) {
        try {
            User userProfile = userMapper.selectUserProfile(userId);

            if (userProfile == null) {
                log.warn("User not found for userId: {}", userId);
                return null;
            }

            // 사용자 포트폴리오 정보를 가져와 자산 총액 계산
            List<Portfolio> portfolios = portfolioMapper.selectOrdersByUserId(userId);
            double totalAssets = portfolios.stream()
                    .mapToDouble(order -> order.getPrice() * order.getQuantity())
                    .sum();

            // User 객체의 프로필 정보를 UserDTO 로 변환
            UserDTO userDTO = UserDTO.builder()
                    .userId(userProfile.getUserId())
                    .nickname(userProfile.getNickname())
                    .investmentTypeName(userProfile.getInvestmentType() != null ? userProfile.getInvestmentType().getInvestmentTypeName() : null)
                    .totalAssets(totalAssets)
                    .profileImageUrl(userProfile.getProfileImageUrl())
                    .name(userProfile.getName())
                    .build();

            log.info("User profile and total assets retrieved: {}", userDTO);
            return userDTO;

        } catch (Exception e) {
            log.error("Failed to retrieve user profile for userId: {}", userId, e);
            return null;
        }
    }
}
