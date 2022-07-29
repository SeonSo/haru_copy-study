package com.project1.haruco.web.dto.query;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainPageQueryDto {
    private Challenge challenge;
    private LocalDateTime modifiedAt;
    private ChallengeRecord challengeRecord;
    private Long memberId;
}
