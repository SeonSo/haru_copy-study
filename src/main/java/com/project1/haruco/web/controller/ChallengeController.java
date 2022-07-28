package com.project1.haruco.web.controller;

import com.project1.haruco.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChallengeController {
    private final ChallengeService challengeService;

}
