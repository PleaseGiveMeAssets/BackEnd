package com.example.spring.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //인코딩 필터 설정
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        return new Filter[]{encodingFilter};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class, SwaggerConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/",
                "*.do",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/**"
        };
    }
}
