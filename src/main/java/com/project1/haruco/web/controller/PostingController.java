package com.project1.haruco.web.controller;

import com.project1.haruco.service.PostingService;
import com.project1.haruco.web.dto.response.posting.PostingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostingController {
    private final PostingService postingService;

    @GetMapping("/api/member/posting")
    public ResponseEntity<Long> createPosting(@ResponseBody PostingRequestDto postingRequestDto){
        return ResponseEntity.ok().body(postingService.createPosting(postingRequestDto))
    }
}
