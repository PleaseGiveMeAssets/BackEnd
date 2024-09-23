package com.example.spring.service;

import com.example.spring.dto.OrderDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final SqlSessionFactory sqlSessionFactory;

    public List<OrderDTO> getPortfolio(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PortfolioMapper portfolioMapper = sqlSession.getMapper(PortfolioMapper.class);
        List<OrderVO> orderVOList = portfolioMapper.findByUserId(userId);

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderVO orderVO : orderVOList) {
            orderDTOList.add(OrderDTO.of(orderVO));
        }
        log.info("portfolioDTOList : {}", orderDTOList);
        log.info(System.getProperty("user.dir"));
        return orderDTOList;
    }

}
