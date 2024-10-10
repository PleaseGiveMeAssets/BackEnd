package com.example.spring.service;

import com.example.spring.dto.InterestCategoryDTO;
import com.example.spring.mapper.InterestCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InterestCategoryServiceImpl implements InterestCategoryService {

    private final InterestCategoryMapper interestCategoryMapper;

    @Autowired
    public InterestCategoryServiceImpl(InterestCategoryMapper interestCategoryMapper) {
        this.interestCategoryMapper = interestCategoryMapper;
    }

    // 메인 카테고리 전체 불러오기
    @Override
    public List<InterestCategoryDTO> getAllMainCategories() {
        return interestCategoryMapper.getAllMainCategories();
    }

    // 사용자가 등록한 관심 세부 카테고리 불러오기
    @Override
    public List<InterestCategoryDTO> getUserSubCategories(String userId) {
        return interestCategoryMapper.getUserSubCategories(userId);
    }

    // 사용자가 등록한 관심 세부 카테고리 삭제
    @Override
    public int deleteSubCategory(String userId, int subCategoryId) {
        return interestCategoryMapper.deleteSubCategory(userId, subCategoryId);
    }

    // 메인 카테고리에 따른 세부 카테고리 목록 불러오기
    @Override
    public List<InterestCategoryDTO> getSubCategoriesByMainCategoryId(int mainCategoryId) {
        return interestCategoryMapper.getSubCategoriesByMainCategoryId(mainCategoryId);
    }

    // 사용자가 선택한 세부 카테고리 저장
    @Override
    public void saveInterestCategory(String userId, int subCategoryId) {
        interestCategoryMapper.saveInterestCategory(userId, subCategoryId);
    }
}
