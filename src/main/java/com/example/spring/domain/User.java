package com.example.spring.domain;

import com.example.spring.vo.UserAnswerVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String password;
    private Integer passwordFailureCount;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String phoneVerificationCode;
    private String phoneVerifiedStatus;
    private String name;
    private Date birthDate;
    private String nickname;
    private String email;
    private String emailVerificationCode;
    private Character emailVerifiedStatus;
    private Character memberStatus;
    private Character surveyStatus;
    private Character alarmStatus;
    private Time reportTime;
    private String profileImageUrl;
    private Character autoLogin;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Notification> notificationList;
    private InvestmentType investmentType;
    private List<UserAnswerVO> userAnswerVOList;
    private List<SavedReport> savedReportList;
    private List<DailyReport> dailyReportList;
    private List<Portfolio> portfolioList;
    private List<RecommendStock> recommendStockList;
    private List<InterestCategory> interestCategoryList;
    private List<SelectedTerms> selectedTermsList;
    private Setting setting;
    private String sns;


    // user 프로필 조회를 위한 생성자
    public User(String userId, String profileImageUrl, String nickname,String name) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.name = name;
    }
}
