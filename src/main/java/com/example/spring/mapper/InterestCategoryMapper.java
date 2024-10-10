package com.example.spring.mapper;

import com.example.spring.dto.InterestCategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterestCategoryMapper {

    // 메인 카테고리 전체 불러오기
    List<InterestCategoryDTO> getAllMainCategories();

    // 사용자가 등록한 관심 세부 카테고리 불러오기
    List<InterestCategoryDTO> getUserSubCategories(String userId);

    // 사용자가 등록한 관심 세부 카테고리 삭제
    int deleteSubCategory(@Param("userId") String userId, @Param("subCategoryId") int subCategoryId);

    // 메인 카테고리에 따른 세부 카테고리 목록 불러오기
    List<InterestCategoryDTO> getSubCategoriesByMainCategoryId(@Param("mainCategoryId") int mainCategoryId);

    // 사용자가 선택한 세부 카테고리 저장
    int saveInterestCategory(@Param("userId") String userId, @Param("subCategoryId") int subCategoryId);
}
