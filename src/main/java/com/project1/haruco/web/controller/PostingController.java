package com.project1.haruco.web.controller;

import com.project1.haruco.service.PostingService;
import com.project1.haruco.web.dto.response.posting.PostingRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/posting")
public class PostingController {
    private final PostingService postingService;

    @PostMapping
    public ResponseEntity<Long> createPosting(@ResponseBody PostingRequestDto postingRequestDto){
        return ResponseEntity.ok().body(postingService.createPosting(postingRequestDto))
    }
    @GetMapping("/api/member/posting")
    public ResponseEntity<List<PostingResponseDto>> getPosting (@PathVariable int page,@PathVariable Long challengeId){
        return ResponseEntity.ok().body(postingService.getPosting(page,challengeId));
    }
    @PutMapping("/update/{postingId}")
    public ResponseEntity<Long> updatePosting(@PathVariable Long postingId,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody PostingRequestDto postingRequestDto){
        log.info("postingRequestDto : {} ",postingRequestDto);
        String email = userDetails.getUsername();
        return ResponseEntity.ok().body(postingService.updatePosting(postingId,email,postingRequestDto));
    }
    @PutMapping("/delete/{postingId}")
    public ResponseEntity<Long> deletePosting(@PathVariable Long postingId,@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return ResponseEntity.ok().body(postingService.deletePosting(postingId,email));
    }
}
