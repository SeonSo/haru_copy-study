package com.project1.haruco.web.dto.request.challengeRecord;

import lombok.NoArgsConstructor;
import lombok.Getter;

@NoArgsConstructor
@Getter
public class ChallengeRecordRequestDto {
    private Long challengeId;
    private String challengePassword;
}
