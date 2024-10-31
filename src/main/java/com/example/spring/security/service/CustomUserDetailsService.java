package com.example.spring.security.service;

import com.example.spring.domain.Member;
import com.example.spring.mapper.MemberMapper;
import com.example.spring.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Autowired
    public CustomUserDetailsService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.findByMemberId(username);

        if (member != null) {
            // 비밀번호가 null인 경우 빈 문자열로 대체
            String password = member.getPassword();

            if (member.getSns() != null) {
                // 소셜 로그인 사용자
                password = password != null ? password : "";
            } else {
                // 일반 사용자
                if (password == null) {
                    throw new IllegalArgumentException("일반 사용자의 비밀번호는 null일 수 없습니다.");
                }
            }
            return org.springframework.security.core.userdetails.User.withUsername(member.getMemberId())
                    .password(password)
                    .roles("MEMBER")
                    .build();
        }
        throw new UsernameNotFoundException(ResultCodeEnum.SESSION_EXPIRATION.getMessage());
    }
}

