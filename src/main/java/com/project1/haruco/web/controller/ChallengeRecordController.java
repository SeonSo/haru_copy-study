package com.project1.haruco.web.controller;

import com.project1.haruco.service.ChallengeRecordService;
import com.project1.haruco.web.dto.request.challengeRecord.ChallengeRecordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ChallengeRecordController {

    private final ChallengeRecordService challengeRecordService;

    @PostMapping("/api/member/challenge-request")
    public Map<String, String> requestChallenge(@RequestBody ChallengeRecordRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        return challengeRecordService.requestChallenge(requestDto, userDetails);
    }

    @DeleteMapping("/api/member/challenge-give-up/{challengeId}")
    public void giveUpChallenge(@PathVariable Long challengeId) {
        challengeRecordService.giveUpChallenge(challengeId);
    }
}
