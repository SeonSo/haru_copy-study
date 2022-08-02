package com.project1.haruco.web.controller;

import com.project1.haruco.service.ChallengeService;
import com.project1.haruco.web.dto.request.challenge.ChallengeRequestDto;
import com.project1.haruco.web.dto.request.challenge.PutChallengeRequestDto;
import com.project1.haruco.web.dto.response.challenge.ChallengeDetailResponseDto;
import com.project1.haruco.web.dto.response.challenge.ChallengeMainResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    //비로그인 메인페이지
    @GetMapping("/api/guest/main")
    public ChallengeMainResponseDto getGuestMainChallengeDetail() {
        return challengeService.getMainPage();
    }

    //챌린지 상세
    @GetMapping("/api/guest/challenge/{challengeId}")
    public ChallengeDetailResponseDto getChallengeDetail(@PathVariable Long challengeId) {
        return challengeService.getChallengeDetail(challengeId);
    }

    //챌린지 등록
    @PostMapping("/api/member/challenge")
    public Long createChallenge(@RequestBody ChallengeRequestDto requestDto,
                                @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST challenge title : {} ", requestDto.getChallengeTitle());
        return challengeService.postChallenge(requestDto, userDetails.getUsername());
    }


    //챌린지 수정
    @PutMapping("/api/member/challenge")
    public void putChallenge(@RequestBody PutChallengeRequestDto requestDto,
                             @AuthenticationPrincipal UserDetails userDetails) {
        log.info("PUT challenge id : {} ", requestDto.getChallengeId());
        challengeService.putChallenge(requestDto, userDetails.getUsername());
    }

    //챌린지 취소
    @DeleteMapping("/api/member/challenge/{challengeId}")
    public void deleteChallenge(@PathVariable Long challengeId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        log.info("DELETE challenge : {} ", challengeId);
        challengeService.deleteChallenge(challengeId, userDetails.getUsername());
    }
}
