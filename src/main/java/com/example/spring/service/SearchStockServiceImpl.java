package com.example.spring.service;

import com.example.spring.dto.StockDTO;
import com.example.spring.mapper.SearchStockMapper;
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
public class SearchStockServiceImpl implements SearchStockService {
    private final SqlSessionFactory sqlSessionFactory;

    /**
     *
     * @param stockName
     * @return
     */
    @Override
    public List<StockDTO> getStocksList(String stockName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SearchStockMapper searchStockMapper = sqlSession.getMapper(SearchStockMapper.class);
        List<StockVO> stockVOList = searchStockMapper.findByStockName(stockName);
        List<StockDTO> stockDTOList = new ArrayList<>();

        for(StockVO stockVO : stockVOList){
            stockDTOList.add(StockDTO.of(stockVO));
        }
        log.info("stockDTOList : {}", stockDTOList);
        log.info(System.getProperty("user.dir"));
        return stockDTOList;
    }
}
