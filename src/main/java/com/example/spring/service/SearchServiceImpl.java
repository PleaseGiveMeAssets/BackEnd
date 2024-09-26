package com.example.spring.service;

import com.example.spring.dto.MyStockDTO;
import com.example.spring.dto.StockDTO;
import com.example.spring.mapper.SearchMapper;
import com.example.spring.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SqlSessionFactory sqlSessionFactory;


    @Override
    public List<StockDTO> getStocksList(String stockName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SearchMapper searchMapper = sqlSession.getMapper(SearchMapper.class);
        List<StockVO> stockVOList = searchMapper.findByStockName(stockName);
        List<StockDTO> stockDTOList = new ArrayList<>();

        for(StockVO stockVO : stockVOList){
            stockDTOList.add(StockDTO.of(stockVO));
        }
        log.info("stockDTOList : {}", stockDTOList);
        log.info(System.getProperty("user.dir"));
        return stockDTOList;
    }

    @Override
    public List<MyStockDTO> getMyAllStocksList(String userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SearchMapper searchMapper = sqlSession.getMapper(SearchMapper.class);

        List<MyStockDTO> stockDTOList =searchMapper.findMyAllStockByUserId(userId);
        log.info("stockDTOList : {}", stockDTOList);
        log.info(System.getProperty("user.dir"));
        return stockDTOList;
    }


    @Override
    public List<MyStockDTO> getMyStocksList(String userId, String stockName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SearchMapper searchMapper = sqlSession.getMapper(SearchMapper.class);

        List<MyStockDTO> stockDTOList = searchMapper.findByMyStockList(userId,stockName);
//        List<StockDTO> stockDTOList = new ArrayList<>();
//
//        for(StockVO stockVO : stockVOList){
//            stockDTOList.add(StockDTO.of(stockVO));
//        }
        log.info("stockDTOList : {}", stockDTOList);
        log.info(System.getProperty("user.dir"));
        return stockDTOList;
    }

}
