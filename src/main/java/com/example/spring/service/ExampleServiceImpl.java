package com.example.spring.service;

import com.example.spring.dto.ExampleDTO;
import com.example.spring.mapper.ExampleMapper;
import com.example.spring.vo.ExampleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExampleServiceImpl implements ExampleService {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ExampleServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<ExampleDTO> getListExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ExampleMapper exampleMapper = sqlSession.getMapper(ExampleMapper.class);
        List<ExampleVO> exampleVOList = exampleMapper.findAll();

        List<ExampleDTO> exampleDTOList = new ArrayList<>();
        for (ExampleVO exampleVO : exampleVOList) {
            exampleDTOList.add(ExampleDTO.of(exampleVO));
        }
        log.info("exampleDTOList : {}", exampleDTOList);
        log.info(System.getProperty("user.dir"));
        return exampleDTOList;
    }
}
