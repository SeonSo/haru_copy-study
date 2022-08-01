package com.project1.haruco.web.controller;

import com.project1.haruco.service.ChallengeService;
import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.dto.request.challenge.ChallengeRequestDto;
import com.project1.haruco.web.dto.request.challenge.PutChallengeRequestDto;
import com.project1.haruco.web.dto.response.challenge.ChallengeListResponseDto;
import com.project1.haruco.web.dto.response.challenge.ChallengeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/api/guest/main")
    public ChallengeGuestMainResponseDto getGuestMainChallengeDetail() {
        return challengeService.getGuestMainChallengeDetail();
    }

    @GetMapping("/api/member/main")
    public ChallengeMemberMainResponseDto getMemberMainChallengeDetail(@AuthenticationPrincipal UserDetails userDetails) {
        return challengeService.getMemberMainChallengeDetail(userDetails.getUsername());
    }

    @GetMapping("/api/member/challenge/{challengeId}")
    public ChallengeResponseDto getChallengeDetail(@PathVariable Long challengeId) {
        return challengeService.getChallengeDetail(challengeId);
    }

    @PostMapping("/api/member/challenge")
    public void createChallenge(@RequestBody ChallengeRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        challengeService.createChallenge(requestDto, userDetails.getUsername());
    }

    @PutMapping("/api/member/challenge")
    public void putChallenge(@RequestBody PutChallengeRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        challengeService.putChallenge(requestDto, userDetails.getUsername());
    }

    @DeleteMapping("/api/member/challenge/{challengeId}")
    public Map<String, String> deleteChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal UserDetails userDetails) {
        return challengeService.deleteChallenge(challengeId, userDetails.getUsername());
    }

    @DeleteMapping("/api/admin/challenge/{challengeId}")
    public void deleteChallengeByAdmin(@PathVariable Long challengeId) {
        challengeService.deleteChallengeByAdmin(challengeId);
    }

    @GetMapping("/api/member/challenge/category/{page}/{categoryName}") // 카테고리 별 조회
    public ChallengeListResponseDto getChallengeByCategoryName(@PathVariable int page,
                                                               @PathVariable CategoryName categoryName) {
        return challengeService.getChallengeByCategoryName(categoryName, page);
    }

    @GetMapping("/api/guest/search/{page}/{searchWords}") // 제목 검색
    public ChallengeListResponseDto getChallengeSearchResult(@PathVariable int page,
                                                             @PathVariable String searchWords) {
        return challengeService.getChallengeSearchResult(searchWords, page);
    }

    @GetMapping("/api/admin/challenge") // 전체 조회 (DB 확인용)
    public List<Challenge> getAllChallenge() {
        return challengeService.getAllChallenge();
    }
}
