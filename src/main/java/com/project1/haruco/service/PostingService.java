package com.project1.haruco.service;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final MenmberRepository menmberRepository;
    private final ChallengeRepository challengeRepository;

    public Long createPosting(PostingRequestDto postingRequestDto){
        Member member = getMember(postingRequestDto);
        Challenge Challenge = getChallenge(postingRequestDto);

        Posting posting = Posting.createPosting(postingRequestDto,member,challenge);
        postingRepository.save(posting);

        return posting.getPostingId();
    }
    private Challenge getChallenge(PostingRequestDto postingRequestDto){
        return challengeRepository.findById(postingRequestDto.getChallenge().getId()).orElseThrow(() -> new ApiRequestException("등록된 챌린지가 없습니다."));
    }

    private Member getMember(PostingRequestDto postingRequestDto){
        return memberRepository.findById(postingRequestDto.getMember().getId()).orElseThrow(() -> new ApiRequestException("등록된 멤버가 없습니다."));
    }
}



