package com.project1.haruco.web.controller;

import com.project1.haruco.service.ChallengeSearchService;
import com.project1.haruco.web.dto.response.challenge.ChallengeListResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChallengeSearchController {

    private final ChallengeSearchService challengeSearchService;

    //제목으로 검색
    @GetMapping("/api/guest/search/{searchWords}/page")
    public ChallengeListResponseDto getChallengeSearchResult(@PathVariable int page,
                                                             @PathVariable String searchWords){
        return challengeSearchService.getChallengeSearchResult(searchWords, page);
    }

    //소팅으로 검색
    @GetMapping("/api/guest/search/{word}/{categoryName}/{period}/{progress}/{page}")
    public ChallengeListResponseDto getChallengeSearchByCategoryNameAndPeriod(@PathVariable String word,
                                                                              @PathVariable String categoryName,
                                                                              @PathVariable int period,
                                                                              @PathVariable int progress,
                                                                              @PathVariable int page) {
        return challengeSearchService.getChallengeBySearch(word, categoryName, period, progress, page);
    }

    //전체보기
    @GetMapping("/api/guest/challenges/{page}")
    public ChallengeListResponseDto getChallenges(@PathVariable int page) {
        return challengeSearchService.getChallengeBySearch("ALL", "ALL", 0, 0, page);
    }
}
