package com.example.spring.security.filter;

import com.example.spring.security.util.JwtProcessor;
import com.example.spring.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;

    @Autowired
    private final RedisService redisService;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private Authentication getAuthentication(HttpServletResponse response, String token) {
        String username = jwtProcessor.getUsername(token);

        if (jwtProcessor.validateToken(token)) {
            UserDetails principal = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        } else {
            String refreshToken = redisService.getRefreshToken(username);
            if(!jwtProcessor.validateToken(refreshToken)){
                return null;
            }
            String newAccessToken = jwtProcessor.generateToken(username);
            String newRefreshToken = jwtProcessor.generateRefreshToken(username);
            redisService.storeRefreshToken(username, newRefreshToken);
            response.addCookie(new Cookie("accessToken", newAccessToken));
        }
        return null;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length());

            if (log.isDebugEnabled()) {
                log.debug("JWT Token: {}", token);
            }

            // 토큰에서 사용자 정보 추출 및 Authentication 객체 구성 후 SecurityContext에 저장
            Authentication authentication = getAuthentication(response, token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
