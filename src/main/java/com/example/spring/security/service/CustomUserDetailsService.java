package com.example.spring.security.service;

import com.example.spring.domain.User;
import com.example.spring.mapper.UserMapper;
import com.example.spring.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserId(username);

        if (user != null) {
            // 비밀번호가 null인 경우 빈 문자열로 대체
            String password = user.getPassword();

            if (user.getSns() != null) {
                // 소셜 로그인 사용자
                password = password != null ? password : "";
            } else {
                // 일반 사용자
                if (password == null) {
                    throw new IllegalArgumentException("일반 사용자의 비밀번호는 null일 수 없습니다.");
                }
            }
            return org.springframework.security.core.userdetails.User.withUsername(user.getUserId())
                    .password(password)
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException(ResultCodeEnum.SESSION_EXPIRATION.getMessage());
    }
}

