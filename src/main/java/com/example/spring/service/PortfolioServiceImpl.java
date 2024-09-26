package com.example.spring.service;

import com.example.spring.dto.ForChartDTO;
import com.example.spring.mapper.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public List<ForChartDTO> getOrderList(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<ForChartDTO> forChartDTOList = portfolioMapper.findByUserId(userId);

        log.info("ForChartDTOList : {}", forChartDTOList);
        log.info(System.getProperty("user.dir"));
        return forChartDTOList;
    }

}
