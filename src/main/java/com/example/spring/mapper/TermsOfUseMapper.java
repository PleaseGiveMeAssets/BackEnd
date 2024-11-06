package com.example.spring.mapper;

import com.example.spring.domain.TermsOfUse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TermsOfUseMapper {
    List<TermsOfUse> selectListTermsOfUseByRequired(String required);

    List<TermsOfUse> selectListTermsOfUse();
}
