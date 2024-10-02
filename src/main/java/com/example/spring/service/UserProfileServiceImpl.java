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
        // 사용자 정보를 가져옴
        User userProfile = userMapper.selectUserProfile(userId);

        if (userProfile == null) {
            log.warn("User not found for userId: {}", userId);
            return null;
        }

        // 사용자의 주문 정보를 가져옴
        List<Portfolio> portfolios = portfolioMapper.selectOrdersByUserId(userId);

        // 자산 총액을 계산
        double totalAssets = portfolios.stream()
                .mapToDouble(order -> order.getPrice() * order.getQuantity())
                .sum();

        // UserVO -> UserDTO 변환
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userProfile, userDTO);
        userDTO.setTotalAssets(totalAssets); // 자산 총액 설정

        log.info("User profile and total assets retrieved: {}", userDTO);

        return userDTO;
    }
}
