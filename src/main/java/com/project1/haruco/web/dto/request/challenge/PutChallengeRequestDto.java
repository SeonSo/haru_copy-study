package com.project1.haruco.web.dto.request.challenge;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PutChallengeRequestDto {
    private Long challengeId;
    private String challengeContent;
    private String challengeTitle;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private String challengeImgUrl;
    private String challengePassword;
    private String challengeGood;
    private String challengeBad;
    private String challengeHoliday;
}
