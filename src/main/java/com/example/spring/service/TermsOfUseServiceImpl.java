package com.example.spring.service;

import com.example.spring.domain.TermsOfUse;
import com.example.spring.dto.TermsOfUseDTO;
import com.example.spring.mapper.TermsOfUseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TermsOfUseServiceImpl implements TermsOfUseService {
    private final TermsOfUseMapper termsOfUseMapper;

    @Autowired
    public TermsOfUseServiceImpl(TermsOfUseMapper termsOfUseMapper) {
        this.termsOfUseMapper = termsOfUseMapper;
    }

    @Override
    public List<TermsOfUseDTO> getTermsOfUse() {
        List<TermsOfUse> termsOfUseList = termsOfUseMapper.selectListTermsOfUse();

        if (log.isInfoEnabled()) {
            log.info("getTermsOfUse termsOfUseList : {}", termsOfUseList.toString());
        }

        return termsOfUseList.stream()
                .map(termsOfUse -> TermsOfUseDTO.builder()
                        .termOfUseId(termsOfUse.getTermsOfUseId())
                        .title(termsOfUse.getTitle())
                        .content(termsOfUse.getContent())
                        .required(termsOfUse.getRequired())
                        .build())
                .collect(Collectors.toList());
    }
}
