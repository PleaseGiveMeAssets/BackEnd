package com.example.spring.service;

import com.example.spring.dto.UserDTO;
import com.example.spring.mapper.OrderMapper;
import com.example.spring.vo.OrderVO;
import com.example.spring.vo.UserVO;
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
    private final OrderMapper orderMapper;

    @Autowired
    public UserProfileServiceImpl(UserMapper userMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public UserDTO getUserProfile(String userId) {
        // 사용자 정보를 가져옴
        UserVO userProfile = userMapper.selectUserProfile(userId);

        if (userProfile == null) {
            log.warn("User not found for userId: {}", userId);
            return null;
        }

        // 사용자의 주문 정보를 가져옴
        List<OrderVO> orders = orderMapper.selectOrdersByUserId(userId);

        // 자산 총액을 계산
        double totalAssets = orders.stream()
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
