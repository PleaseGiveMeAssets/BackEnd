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
            return org.springframework.security.core.userdetails.User.withUsername(user.getUserId())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException(ResultCodeEnum.SESSION_EXPIRATION.getMessage());
    }
}

