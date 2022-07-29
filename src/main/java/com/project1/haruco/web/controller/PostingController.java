package com.project1.haruco.web.controller;

import com.project1.haruco.service.PostingService;
import com.project1.haruco.web.dto.request.posting.PostingCreateRequestDto;
import com.project1.haruco.web.dto.request.posting.PostingRequestDto;
import com.project1.haruco.web.dto.response.posting.PostingListDto;
import com.project1.haruco.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posting")
public class PostingController {
    private final PostingService postingService;

    //저장
    @PostMapping("")
    public Long createPosting(@RequestBody @Valid PostingCreateRequestDto postingRequestDto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        log.info("createPosting 포스트 저장: {} ", postingRequestDto);
        String email = userDetails.getUsername();
        return postingService.createPosting(postingRequestDto, email);
    }

    //리스트
    @GetMapping("/{page}/{challengeId}")
    public PostingListDto getPosting(@PathVariable int page,
                                     @PathVariable Long challengeId) {
        log.info("getPosting 전체 포스트 리스트 : {} ", challengeId);
        return postingService.getPosting(page, challengeId);
    }

    //업데이트
    @PutMapping("/update/{postingId")
    public ResponseEntity<Long> updatePosting(@PathVariable Long postingId,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody PostingRequestDto postingRequestDto){
        log.info("postingRequestDto : {}",postingRequestDto);
        String email = userDetails.getUsername();
        return ResponseEntity.ok().body(postingService.updatePosting(postingId, email, postingRequestDto));
    }

    //삭제
    @DeleteMapping("/delete/{postingId}")
    public Long deletePosting(@PathVariable Long postingId,
                              @AuthenticationPrincipal UserDetails userDetails) {
        log.info("deletePosting 포스트 삭제: {} ", postingId);
        String email = userDetails.getUsername();
        return postingService.deletePosting(postingId, email);
    }
}
