package com.project1.haruco.web.dto.response.mypage.history;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PointHistoryDto {
    private Long pointHistoryId;
    private LocalDate createdAt;
    private String challengeTitle;
    private Long getPoint;

    public PointHistoryDto(MemberHistoryDto dto){
        this.pointHistoryId = dto.getPointHistoryId();
        this.createdAt = changeLocalDate(dto.getCreatedAt());
        this.challengeTitle = dto.getChallengeTitle();
        this.getPoint = dto.getGetPoint();
    }

    private LocalDate changeLocalDate(LocalDateTime localDateTime){
        return localDateTime.toLocalDate();
    }
}
