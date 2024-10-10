package com.example.spring.service;

import com.example.spring.domain.DailyReport;
import com.example.spring.domain.SavedReport;
import com.example.spring.mapper.SavedReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SavedReportServiceImpl implements SavedReportService {

    @Autowired
    private final SavedReportMapper savedReportMapper;

    // 유저 ID를 사용하여 저장된 리포트를 불러옴
    @Override
    public List<DailyReport> getSavedReports(String userId) {
        log.info("유저 ID {}의 저장된 리포트를 불러옵니다.", userId);
        return savedReportMapper.findAllSavedReportsByUserId(userId);
    }
    // 저장된 리포트를 DB에 저장
    @Override
    public void saveReport(Long dailyReportId, String userId) {
        log.info("리포트 ID {}를 유저 ID {}로 저장합니다.", dailyReportId, userId);
        savedReportMapper.save(new SavedReport(null, userId, dailyReportId, null, null));
    }
    // 저장된 리포트 삭제
    @Override
    public void deleteReport(String userId,Long dailyReportId) {
        log.info("유저 ID {}의 저장된 리포트 ID {}를 삭제합니다.",userId, dailyReportId);
        savedReportMapper.deleteByUserIdAndSavedReportId(userId,dailyReportId);
        log.info("유저 ID {}의 리포트 ID {}를 성공적으로 삭제했습니다.", userId, dailyReportId);
    }
}
