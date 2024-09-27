package com.example.spring.service;

import com.example.spring.dto.ExampleDTO;
import com.example.spring.dto.PortfolioPurchaseDTO;
import com.example.spring.mapper.PortfolioPurchaseMapper;
import com.example.spring.vo.PortfolioVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioPurchaseServiceImpl implements PortfolioPurchaseService{
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void saveStock(String userid, PortfolioPurchaseDTO portfolioPurchaseDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioPurchaseMapper portfolioPurchaseMapper = sqlSession.getMapper(PortfolioPurchaseMapper.class);
        portfolioPurchaseMapper.savePortfolio(
                        "testUser1",
                        portfolioPurchaseDTO.getStockName(),
                        portfolioPurchaseDTO.getPrice(),
                        portfolioPurchaseDTO.getQuantity(),
                        portfolioPurchaseDTO.getMemo(),
                        "0",
                        portfolioPurchaseDTO.getOrderedAt()
                        );

        log.info(System.getProperty("user.dir"));
    }
}
