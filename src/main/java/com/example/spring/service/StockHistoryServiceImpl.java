package com.example.spring.service;

import com.example.spring.domain.StockHistory;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.mapper.StockHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockHistoryServiceImpl implements StockHistoryService {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 일일추천종목 조회
     * <p>
     * 유저아이디와 날짜를 사용해 추천종목 리스트를 조회하는 메소드이다.
     *
     * @param stockId
     * @return List<StockHistoryDTO>
     */
    @Override
    public List<StockHistoryDTO> findByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockHistoryMapper stockHistoryMapper = sqlSession.getMapper(StockHistoryMapper.class);
        List<StockHistory> byStockId = stockHistoryMapper.findByStockId(stockId);
        List<StockHistoryDTO> stockHistoryDTOList = new ArrayList<>();
        for (StockHistory stockHistory : byStockId) {
            stockHistoryDTOList.add(new StockHistoryDTO(stockHistory.getStockHistoryId(), stockHistory.getCurrentPrice()));
        }
        return stockHistoryDTOList;
    }
}
