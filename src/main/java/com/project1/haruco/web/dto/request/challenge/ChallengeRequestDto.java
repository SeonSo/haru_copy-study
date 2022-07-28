package com.project1.haruco.web.dto.request.challenge;

import com.project1.haruco.web.domain.challenge.CategoryName;
import lombok.Getter;

@Getter
public class ChallengeRequestDto {
    private String challengeTitle;
    private String challengeContent;
    private String challengePassword;
}
