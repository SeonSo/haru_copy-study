package com.project1.haruco.service;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordRepository;
import com.project1.haruco.web.dto.response.challenge.ChallengeDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeRecordRepository challengeRecordRepository;

    public ChallengeDetailResponseDto getChallengeDetail(Long challengeId){
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(
                () -> new NullPointerException("존재하지 않은 챌린지 입니다"));

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        if(currentLocalDateTime.isBefore(challenge.getChallengeStartDate())){
            challenge.setChallengeProgress(1L);
        } else if (currentLocalDateTime.isBefore(challenge.getChallengeEndDate())) {
            challenge.setChallengeProgress(2L);
        } else {
            challenge.setChallengeProgress(3L);
        }
    }

    List<Long> challengeMember = new ArrayList<>();
    challengeRecordRepository.findAllByChallenge(challenge).forEach()
}
