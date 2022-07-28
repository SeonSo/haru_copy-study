package com.project1.haruco.web.controller;

import com.project1.haruco.service.PostingService;
import com.project1.haruco.web.dto.request.posting.PostingRequestDto;
import com.project1.haruco.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posting")
public class PostingController {
    private final PostingService postingService;

    //저장
    @PostMapping("")
    public ResponseEntity<Long> createPosting(@RequestBody PostingRequestDto postingRequestDto){
        log.info("postingRequestDto : {} ",postingRequestDto);
        return ResponseEntity.ok().body(postingService.createPosting(postingRequestDto));
    }

    //리스트
    @GetMapping("/{page}/{challengeId}")
    public ResponseEntity<Long<PostingResponseDto>> getPosting(@PathVariable int page,
                                                               @PathVariable Long challengeId){
        return ResponseEntity.ok().body(postingService.getPosting(page, challengeId));
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
    @PutMapping("/delete/{postingId}")
    public ResponseEntity<Long> deletePosting(@PathVariable Long postingId,
                                              @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();

        return ResponseEntity.ok().body(postingService.deletePosting(postingId, email));
    }
}
