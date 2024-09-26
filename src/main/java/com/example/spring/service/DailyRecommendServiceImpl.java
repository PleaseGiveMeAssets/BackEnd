package com.example.spring.service;

import com.example.spring.dto.DailyRecommendStockDTO;
import com.example.spring.dto.DailyStockDTO;
import com.example.spring.mapper.StockMapper;
import com.example.spring.vo.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DailyRecommendServiceImpl implements DailyRecommendService {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public DailyRecommendServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 일일추천종목 조회
     * <p>
     * 유저아이디와 날짜를 사용해 추천종목 리스트를 조회하는 메소드이다.
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public List<DailyStockDTO> getDailyRecommendStockInfo(String userId, String date) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StockMapper stockMapper = sqlSession.getMapper(StockMapper.class);
        List<StockVO> stockVOList = stockMapper.selectListRecommendStockByUserId(userId, date);
        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo stockVOList : {}", stockVOList);
        }

        List<DailyStockDTO> dailyStockDTOList = new ArrayList<>();
        if (!stockVOList.isEmpty()) {
            stockVOList.stream().forEach(stockVO -> {
                DailyStockDTO dailyStockDTO = new DailyStockDTO();
                dailyStockDTO.setStockId(stockVO.getStockId());
                dailyStockDTO.setShortCode(stockVO.getShortCode());
                dailyStockDTO.setStockName(stockVO.getStockName());

                List<DailyRecommendStockDTO> dailyRecommendStockDTOList = new ArrayList<>();
                if (!stockVO.getRecommendStockVOList().isEmpty()) {
                    stockVO.getRecommendStockVOList().stream().forEach(recommendStockVO -> {
                        DailyRecommendStockDTO dailyRecommendStockDTO = new DailyRecommendStockDTO();
                        dailyRecommendStockDTO.setRecommendStockId(recommendStockVO.getRecommendStockId());
                        dailyRecommendStockDTO.setUserId(recommendStockVO.getUserId());
                        dailyRecommendStockDTO.setContent(recommendStockVO.getContent());
                        dailyRecommendStockDTOList.add(dailyRecommendStockDTO);
                    });
                }
                dailyStockDTO.setRecommendStockVOList(dailyRecommendStockDTOList);
                dailyStockDTOList.add(dailyStockDTO);
            });
        }
        return dailyStockDTOList;
    }
}
