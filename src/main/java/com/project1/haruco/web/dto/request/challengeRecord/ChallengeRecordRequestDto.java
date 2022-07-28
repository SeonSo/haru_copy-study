package com.project1.haruco.web.dto.request.challengeRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@NoArgsConstructor
@Getter
public class ChallengeRecordRequestDto {
    private Long challengeId;
    private String challengePassword;
}
