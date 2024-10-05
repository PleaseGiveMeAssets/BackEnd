package com.example.spring.security.service;

import com.example.spring.domain.User;
import com.example.spring.mapper.UserMapper;
import com.example.spring.util.ResultCodeEnum;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public CustomUserDetailsService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
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

